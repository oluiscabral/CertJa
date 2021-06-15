import api from './api';
import CertificateSave from './CertificateSave';

export default class Service {
    private static createCertificateSave(obj: any) {
        return new CertificateSave(obj.id, obj.holder_name, obj.initial_date, obj.end_date, obj.certificate);
    }
    
    private static mapCertificateSaves(it: any): CertificateSave[] | PromiseLike<CertificateSave[]> {
        if (it !== null || it !== undefined) {
            return it.map((obj: any) => {
                return this.createCertificateSave(obj);
            });
        }
        return [];
    }

    private static parseDate(date: Date): string {
        return date.getMonth() + "/" + date.getDay() + "/" + date.getFullYear();
    }
    
    static async findAllCertificateSaves(): Promise<Array<CertificateSave>> {
        return this.mapCertificateSaves((await api.get('/')).data);
    }
    
    static async findCertificateSaves(holderName: string, initialDate: Date, endDate: Date): Promise<Array<CertificateSave>> {
        const result = (await api.get('/find', {
            params: {
                "holder_name": holderName,
                "initial_date": this.parseDate(initialDate),
                "end_date": this.parseDate(endDate)
            }
        })).data;
        return this.mapCertificateSaves(result);
    }
    
    static async findCertificateSavesByHolderName(holderName: string): Promise<Array<CertificateSave>> {
        const result = (await api.get('/find', {
            params: {
                "holder_name": holderName
            }
        })).data;
        return this.mapCertificateSaves(result);
    }
    
    static async findCertificateSavesByInitialDate(holderName: string | null, initialDate: Date): Promise<Array<CertificateSave>> {
        let result = null;
        if (holderName == null) {
            result = (await api.get('/find', {
                params: {
                    "initial_date": this.parseDate(initialDate)
                }
            })).data;
        } else {
            result = (await api.get('/find', {
                params: {
                    "holder_name": holderName,
                    "initial_date": this.parseDate(initialDate)
                }
            })).data;
        }
        return this.mapCertificateSaves(result);
    }

    static async findCertificateSavesByEndDate(holderName: string | null, endDate: Date): Promise<Array<CertificateSave>> {
        let result = null;
        if (holderName == null) {
            result = (await api.get('/find', {
                params: {
                    "end_date": this.parseDate(endDate)
                }
            })).data;
        } else {
            result = (await api.get('/find', {
                params: {
                    "holder_name": holderName,
                    "end_date": this.parseDate(endDate)
                }
            })).data;
        }
        return this.mapCertificateSaves(result);
    }
    
    static async findCertificateSavesByDate(initialDate: Date, endDate: Date): Promise<Array<CertificateSave>> {
        let result = null;
        if (initialDate != null && initialDate != undefined && endDate != null && endDate != undefined) {
            result = (await api.get('/find', {
                params: {
                    "initial_date": this.parseDate(initialDate),
                    "end_date": this.parseDate(endDate)
                }
            })).data;
        } else if (initialDate != null && initialDate != undefined) {
            result = (await api.get('/find', {
                params: {
                    "initial_date": this.parseDate(endDate)
                }
            })).data;
        } else if (endDate != null && endDate != undefined) {
            result = (await api.get('/find', {
                params: {
                    "end_date": this.parseDate(endDate)
                }
            })).data;
        }
        return this.mapCertificateSaves(result);
    }
    
    static async findCertificateSaveById(id: number): Promise<CertificateSave> {
        const result = (await api.get('/find', {
            params: { id }
        })).data;
        return this.createCertificateSave(result);
    }
    
    static async saveCertificateSave(certificateSave: CertificateSave): Promise<CertificateSave> {
        const result = (await api.post('/create', {
            "holder_name": certificateSave.holderName,
            "initial_date": this.parseDate(certificateSave.initialDate),
            "end_date": this.parseDate(certificateSave.endDate)
        })).data;
        return this.createCertificateSave(result);
    }
    
    static async deleteCertificateSaveById(id: number): Promise<number> {
        return (await api.get('/delete', {
            method: 'DELETE',
            params: { id }
        })).data;
    }
}
