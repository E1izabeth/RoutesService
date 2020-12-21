import xml, { IXmlEntity, XmlElement, XmlElementFabric, XmlNode } from './XmlUtils';
import { HttpContext } from './HttpContext';
import { AxiosResponse } from 'axios';

export interface IEntityCreationSpec {
    toXmlSpecElement(x: XmlElementFabric) : XmlElement;
}

export default class DataService<T extends IXmlEntity ,TT> {

    public constructor (
        protected readonly _http: HttpContext,
        protected readonly _url: string,
        protected readonly _entityCtor: (x: XmlNode) => T,
        protected readonly _collectionCtor: (x: XmlNode) => TT
    ) {
    }

    getAll(): Promise<TT> {
        return this._http.get(this._url, this._collectionCtor);
    }

    get(id: number): Promise<T> {
        return this._http.get(this._url + `/${id}`, this._entityCtor);
    }

    create(data: IEntityCreationSpec): Promise<T> {
        return this._http.post(this._url + "?action=add", xml.create(data.toXmlSpecElement.bind(data)), this._entityCtor);
    }

    update(data: T): Promise<AxiosResponse> {
        return this._http.put(this._url + `/${data.id}`, data);
    }

    delete(id: number): Promise<AxiosResponse> {
        return this._http.delete(this._url + `/${id}`);
    }

    findByParams(filter?: string, sort?: string, pageSize?: number, pageNum?: number): Promise<TT> {
        return this._http.get(
            this._url + `?filter=${filter ? encodeURIComponent(filter) : ''}&sort=${sort ? encodeURIComponent(sort) : ''}`
                      + `&page-size=${pageSize ? encodeURIComponent(pageSize) : ''}&page-num=${pageNum ? encodeURIComponent(pageNum) : ''}`,
            this._collectionCtor
        );
    }
}
