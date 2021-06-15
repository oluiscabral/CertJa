export default class CertificateSave {
    id: number | undefined;
    holderName: string;
    initialDate: Date;
    endDate: Date;
    certificate: string | undefined;

    constructor(id: number | undefined, holderName:string, initialDate:Date, endDate:Date, certificate:string | undefined) {
        this.id = id;
        this.holderName = holderName;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.certificate = certificate;
    }
}
