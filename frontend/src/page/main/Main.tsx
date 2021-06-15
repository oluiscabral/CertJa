import React, { ChangeEvent, ChangeEventHandler, Component, LegacyRef } from 'react';
import { useCallback } from 'react';
import { Ref } from 'react';
import { useState } from 'react';
import CertificateSave from '../../service/CertificateSave';
import Service from '../../service/service';


import './main.css';





const Main: React.FC = () => {

    const [certificateSaves, setCertificateSaves] = useState(Array<CertificateSave>());

    const [holderName, setHolderName] = useState('');
    const [initialDateStr, setInitialDateStr] = useState('');
    const [endDateStr, setEndDateStr] = useState('');

    const [certificates] = useState(Object);

    return (
        <div className="main-page">
            <header>
                <div className="logo">
                    <h2>CertJa</h2>
                </div>
            </header>

            <main>

                <div className="query-wrapper">

                    <table className="input-wrapper">

                        <tr>
                            <td>
                                <label htmlFor="holder_name">titular:</label>
                            </td>
                            <td>
                                <input type="text" id="holder_name" value={holderName} onChange={e => setHolderName(e.target.value)} />
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="initial_date">data inicial:</label>
                            </td>
                            <td>
                                <input type="date" id="initial_date" value={initialDateStr} onChange={e => setInitialDateStr(e.target.value)} />
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="end_date">data final:</label>
                            </td>
                            <td>
                                <input type="date" id="end_date" value={endDateStr} onChange={e => setEndDateStr(e.target.value)} />
                            </td>
                        </tr>

                    </table>

                    <div className="button-wrapper">
                        <button onClick={createCertificateSave}>create</button>
                        <button onClick={findCertificateSaves}>search</button>
                    </div>

                </div>

                <table className="result">
                    {loadTableHeader()}
                    {
                        certificateSaves.map(certificateSave => (
                            <tr className="result__row" onClick={() => displayCertificate(certificateSave.id)}>
                                <td>{certificateSave.id}</td>
                                <td>{certificateSave.holderName}</td>
                                <td>{certificateSave.initialDate}</td>
                                <td>{certificateSave.endDate}</td>
                                {putCertificate(certificateSave.id, certificateSave.certificate)}
                            </tr>
                        ))
                    }
                </table>

            </main>

            <footer>

            </footer>

        </div>
    );

    function putCertificate(id: number | undefined, certificate: string | undefined) {
        if (id != undefined && certificate != undefined) {
            certificates[id] = certificate;
        }
    }

    function displayCertificate(id: number | undefined) {
        if (id != undefined) {
            alert(certificates[id]);
        }
    }

    function getDates() {
        let initialDate = getDate(initialDateStr);
        let endDate = getDate(endDateStr);
        return {
            initialDate,
            endDate
        }
    }

    function getDate(dateStr: string) {
        if (dateStr.trim()) {
            return new Date(dateStr);
        }
        return null;
    }

    async function createCertificateSave() {
        const { initialDate, endDate } = getDates();
        if (initialDate && endDate) {
            const certificateSave = new CertificateSave(undefined, holderName, initialDate, endDate, undefined);
            await Service.saveCertificateSave(certificateSave);
        }
    }

    async function findCertificateSaves() {
        const { initialDate, endDate } = getDates();
        let result = null;
        if (holderName.trim()) {
            if (initialDate && endDate) {
                result = await Service.findCertificateSaves(holderName, initialDate, endDate);
            } else if (initialDate) {
                result = await Service.findCertificateSavesByInitialDate(holderName, initialDate);
            } else if (endDate) {
                result = await Service.findCertificateSavesByEndDate(holderName, endDate);
            } else {
                result = await Service.findCertificateSavesByHolderName(holderName);
            }
        } else if (initialDate && endDate) {
            result = await Service.findCertificateSavesByDate(initialDate, endDate);
        } else if (initialDate) {
            result = await Service.findCertificateSavesByInitialDate(null, initialDate);
        } else if (endDate) {
            result = await Service.findCertificateSavesByEndDate(null, endDate);
        } else {
            result = await Service.findAllCertificateSaves();
        }
        setCertificateSaves(result);
    };

    function loadTableHeader() {
        if (certificateSaves.length > 0) {
            return (
                <tr>
                    <th>id</th>
                    <th>titular</th>
                    <th>início (mm/dd/aaaa)</th>
                    <th>final (mm/dd/aaaa)</th>
                </tr>
            );
        }
    }
}

export default Main;
