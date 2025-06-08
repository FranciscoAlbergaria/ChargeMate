import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from "../../components/driver/BottomNavEvDriver.jsx";

// Ícones personalizados
import PinGreen from '../../assets/Green.png';
import PinRed from '../../assets/Red.png';
import PinGray from '../../assets/Gray.png';

const iconAvailable = new L.Icon({
    iconUrl: PinGreen,
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const iconOccupied = new L.Icon({
    iconUrl: PinRed,
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const iconUnknown = new L.Icon({
    iconUrl: PinGray,
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

export default function DashboardEVDriver() {
    const [stations, setStations] = useState([]);

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_API_URL}/api/stations?lat=40.6405&lon=-8.6538&distance=10`)
            .then(res => {
                console.log("Stations fetched:", res.data);
                setStations(res.data);
            })
            .catch(err => console.error("Erro ao carregar estações:", err));
    }, []);


    const getStatusIcon = () => iconAvailable; // Se tiveres lógica de estado, muda aqui

    return (
        <div className="bg-[#202025] text-white min-h-screen relative pb-10">
            <Header />

            <div className="m-4">
                <MapContainer
                    center={[40.6405, -8.6538]}
                    zoom={13}
                    style={{ height: "calc(100vh - 150px)", width: "100%", borderRadius: "1rem" }}

                >
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                    />

                    {stations.map(station => {
                        const info = station.AddressInfo;
                        if (!info || !info.Latitude || !info.Longitude) return null;

                        return (
                            <Marker
                                key={station.ID}
                                position={[station.AddressInfo.Latitude, station.AddressInfo.Longitude]}
                                icon={getStatusIcon()}
                            >
                                <Popup>
                                    <div className="space-y-1">
                                        <strong className="block text-base">{station.AddressInfo.Title}</strong>
                                        <p className="text-sm text-gray-400">{station.AddressInfo.AddressLine1}</p>
                                        <p className="text-sm text-gray-400">{station.AddressInfo.Town ?? ''}</p>
                                        <p className="text-sm text-gray-400">
                                            Power: {station.Connections?.[0]?.PowerKW ?? 'Unknown'} kW<br />
                                            Voltage: {station.Connections?.[0]?.Voltage ?? 'N/A'} V
                                        </p>

                                        <div className="flex items-center justify-between mt-2 gap-2">
                                            <button
                                                className="px-3 py-1 text-sm rounded-full bg-gradient-to-r from-cyan-400 to-blue-500 text-white hover:opacity-90"
                                                onClick={() => alert('Booking not yet implemented')}
                                            >
                                                Book
                                            </button>
                                            <button
                                                className="text-yellow-400 text-xl hover:scale-110 transition-transform"
                                                onClick={() => alert('Favorite not yet implemented')}
                                            >
                                                ★
                                            </button>
                                        </div>
                                    </div>
                                </Popup>
                            </Marker>

                        );
                    })}

                </MapContainer>
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
