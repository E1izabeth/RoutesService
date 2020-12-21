package com.example.routes;

import com.example.routes.db.*;
import com.example.routes.query.QueryTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.sql.SQLException;
import java.util.logging.Logger;

public class MyDao {

    protected final XmlEntityBuilder _xmlBuilder;
    //protected final DbContext _dbCtx;
    protected final Logger _log;

    protected MyDao() throws SQLException {

        String prefix = "http://localhost:8080";//ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString(); // TODO: check it out!
        _xmlBuilder = new XmlEntityBuilder(prefix);

        _log = Logger.getLogger(getClass().getName());

        // _dbCtx = new DbContext(new DbConnection("pg", 5432, "studs", "s243891", "pui193"));
    }

    protected static class FilteringQueryResult<T> {
        public final Iterable<T> data;
        public final long pageSize;
        public final long pageNum;
        public final long pagesCount;
        public final long totalCount;

        public FilteringQueryResult(Iterable<T> data, long pageSize, long pageNum, long pagesCount, long totalCount) {
            this.data = data;
            this.pageSize = pageSize;
            this.pageNum = pageNum;
            this.pagesCount = pagesCount;
            this.totalCount = totalCount;
        }
    }

    protected interface RepositoryAccessor<T> {
        Page<T> doQuery(Specification<T> specification, Pageable pageable);
    }

    protected static class EntitiesSelector<T> {
        private final RepositoryAccessor<T> _accessor;

        public EntitiesSelector(RepositoryAccessor<T> accessor) {
            _accessor = accessor;
        }

        protected FilteringQueryResult<T> selectEntities(String filterStr, String sortStr, Integer pageSize, Integer pageNum) {
            QueryTranslator qt = new QueryTranslator();

            if (pageSize == null)
                pageSize = qt.getDefaultPageSize();
            if (pageNum == null)
                pageNum = 0;

            Specification<T> spec = qt.translateQuery(filterStr);
            Pageable pageParams = PageRequest.of(pageNum, pageSize, qt.translateOrderSpec(sortStr));
            Page<T> page = spec == null ? _accessor.doQuery(Specification.where(null), pageParams) : _accessor.doQuery(spec, pageParams);

            // long pagesCount = (totalCount - 1) / pageSize + 1;
            return new FilteringQueryResult(page.getContent(), pageSize, pageNum, page.getTotalPages(), page.getTotalElements());
        }
    }
}
