

const xpath = (node: Node, expr: string, type?: number) : XPathResult => {
    //const doc = node.ownerDocument || node as Document;
    const doc = node instanceof Document ? node : node.ownerDocument;
    if (!doc)
        throw new Error('Failed to obtain document to apply xpath ' + expr + ': ' + node);

    const nsResolver: XPathNSResolver = (prefix) => {
        var ns = new Map<string|null,string>([
            ['m', 'main.webapp.xml'],
        ]);
        return ns.get(prefix)||null;
    };

    const result = doc.evaluate(expr, node, nsResolver, type);
    return result;
};

const xpathString = (node: Node, expr: string) : string => {
    const result = xpath(node, expr, XPathResult.STRING_TYPE);
    return result.stringValue;
};

const xpathNodes = (node: Node, expr: string) : Node[] => {
    const result = xpath(node, expr);
    var items = [];
    for (var item = result.iterateNext(); item; item = result.iterateNext()) {
        items.push(item);
    }
    return items;
};


export class XmlNode {
    public constructor(
        private readonly _node: Node
    ) {
    }

    public string(xpath: string) : string {
        return xpathString(this._node, xpath);
    }
    public int(xpath: string) : number {
        return Number.parseInt(xpathString(this._node, xpath));
    }
    public float(xpath: string) : number {
        return Number.parseFloat(xpathString(this._node, xpath));
    }

    public nodes(xpath: string) : XmlNode[] {
        return xpathNodes(this._node, xpath).map(n => new XmlNode(n));
    }
    public node(xpath: string) : XmlNode {
        return new XmlNode(xpathNodes(this._node, xpath)[0]);
    }
}

export class XmlElementContent {
    public constructor(
        public readonly attrs: any,
        public readonly children: XmlElement[]
    ) {
    }

    public concat(other: XmlElementContent) : XmlElementContent {
        return new XmlElementContent(
            Object.assign({}, this.attrs, other.attrs),
            this.children.concat(other.children)
        );
    }

    public append(attrs?: any, children?: XmlElement[]): XmlElementContent {
        return this.concat(new XmlElementContent(attrs ?? {}, children ?? Array()));
    }
}
export class XmlElement {
    public constructor(
        public readonly name: string,
        public readonly content: XmlElementContent
    ) {
    }
}

export class XmlElementFabric {
    public element(name: string, data?: any|IXmlContent|XmlElementContent, children?: XmlElement[]) : XmlElement{
        if (data instanceof XmlElementContent) {
            return new XmlElement(name, data);
        } else if (data.toXmlElementContent) {
            return new XmlElement(name, data.toXmlElementContent(this));
        } else {
            return new XmlElement(name, new XmlElementContent(data ?? {}, children ?? Array()));
        }
    }
    public content(attrs?: any, children?: XmlElement[]): XmlElementContent {
        return new XmlElementContent(attrs ?? {}, children ?? Array());
    }
}

export interface IXmlEntity {
    id: number;
    toXmlElement(x: XmlElementFabric) : XmlElement;
}
export interface IXmlContent {
    toXmlElementContent(x: XmlElementFabric) : XmlElementContent;
}

const xml = {
    parse: (text: string) : XmlNode => {
        const parser = new DOMParser();
        const dom = parser.parseFromString(text, "application/xml");
        return new XmlNode(dom.documentElement);
    },
    create: (source: (x:  XmlElementFabric) => XmlElement) : string => {
        const rawDoc: XMLDocument = Object.create(XMLDocument.prototype, {});
        const ns = 'main.webapp.xml';

        const makeElement = (model: XmlElement) : Element => {
            const e = rawDoc.createElementNS.call(document, ns, model.name);

            if (model.content.attrs) {
                for (let key in model.content.attrs) {
                    const value = model.content.attrs[key];
                    e.setAttribute(key, value);
                }
            }

            if (model.content.children) {
                model.content.children.map(makeElement).forEach(c => e.appendChild(c));
            }

            return e;
        };

        const tree = source(new XmlElementFabric());

        //rawDoc.appendChild(makeElement(tree));
        const root = makeElement(tree);

        const serializer = new XMLSerializer();
        const result = serializer.serializeToString(root);
        return result;
    }
};

export default xml;
