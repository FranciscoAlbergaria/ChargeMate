import React, { useState } from 'react';
import Header from '../../components/shared/Header.jsx';
import SearchBar from '../../components/driver/SearchBar.jsx';
import StationCard from '../../components/shared/StationCard.jsx';
import BottomNavEvDriver from "../../components/driver/BottomNavEvDriver.jsx";
import PinRed from '../../assets/Red.png';
import PinGreen from '../../assets/Green.png';
import PinGray from '../../assets/Gray.png';

export default function DashboardEVDriver() {
    const [selectedStation, setSelectedStation] = useState(null);
    const [cardPosition, setCardPosition] = useState({ top: 0, left: 0 });

    const stations = [
        {
            id: 1,
            name: 'GreenCharge SF',
            address: '145 Casa Bella Dr., San Francisco, CA 94118',
            status: 'Available',
            type: 'Type 2',
        },
        {
            id: 2,
            name: 'Volt Station',
            address: '330 Castro St., San Francisco, CA 94114',
            status: 'Occupied',
            type: 'CCS',
        },
    ];

    return (
        <div className="bg-[#202025] text-white min-h-screen relative pb-10">
            <Header />
            <SearchBar />

            {/* Mapa com marcadores */}
            <div className="relative h-[500px] bg-gray-800 m-4 rounded-xl overflow-hidden">
                {stations.map((station, index) => {
                    const getStatusIcon = (status) => {
                        if (status === 'Available') return PinGreen;
                        if (status === 'Occupied') return PinRed;
                        return PinGrey; // for Maintenance or other states
                    };


                    const positions = [
                        { top: '30%', left: '35%' },
                        { top: '55%', left: '60%' },
                    ];

                    return (
                        <img
                            key={station.id}
                            src={getStatusIcon(station.status)}
                            alt={station.status}
                            onClick={(e) => {
                                const rect = e.currentTarget.getBoundingClientRect();
                                const mapRect = e.currentTarget.offsetParent.getBoundingClientRect();
                                const top = rect.top - mapRect.top;
                                const left = rect.left - mapRect.left;
                                setSelectedStation(station);
                                setCardPosition({top, left});
                            }}
                            className="absolute w-10 h-10 cursor-pointer transition-transform hover:scale-110"

                            style={{
                                top: positions[index].top,
                                left: positions[index].left,
                            }}
                            title={`${station.name} (${station.status})`}
                        />

                    );
                })}

                {/* Station info card */}
                {selectedStation && (
                    <StationCard
                        station={selectedStation}
                        address={selectedStation.address}
                        onBook={handleBook}
                        onClose={() => setSelectedStation(null)}
                    />
                )}

                <BottomNavEvDriver/>

            </div>
        </div>
    );
}
