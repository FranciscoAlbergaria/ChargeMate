import React, { useState } from 'react';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

const stationsMock = [
    {
        id: 1,
        name: 'Station Alpha',
        totalSessions: 120,
        totalEnergy: 4800, // in kWh
        avgDuration: 38,   // in minutes
    },
    {
        id: 2,
        name: 'Station Beta',
        totalSessions: 86,
        totalEnergy: 3200,
        avgDuration: 42,
    },
];

export default function Analytics() {
    const [selectedStation, setSelectedStation] = useState(null);

    return (
        <div className="bg-[#202025] text-white min-h-screen pb-20">
            <Header />

            <div className="p-5 space-y-4">
                <h1 className="text-2xl font-bold">Usage Analytics</h1>

                {/* Station Selector */}
                <div className="space-y-2">
                    <p className="text-sm text-gray-400">Select a station:</p>
                    <div className="grid grid-cols-1 gap-2">
                        {stationsMock.map((station) => (
                            <button
                                key={station.id}
                                onClick={() => setSelectedStation(station)}
                                className={`btn w-full ${
                                    selectedStation?.id === station.id
                                        ? 'btn-primary'
                                        : 'btn-outline'
                                }`}
                            >
                                {station.name}
                            </button>
                        ))}
                    </div>
                </div>

                {/* KPIs Display */}
                {selectedStation && (
                    <div className="mt-6 space-y-4 bg-gray-800 p-5 rounded-xl shadow-md">
                        <h2 className="text-xl font-semibold mb-2">{selectedStation.name}</h2>
                        <div className="grid grid-cols-1 gap-4 text-center">
                            <div className="bg-gray-900 p-4 rounded-xl">
                                <p className="text-sm text-gray-400">Total Sessions</p>
                                <p className="text-2xl font-bold">{selectedStation.totalSessions}</p>
                            </div>
                            <div className="bg-gray-900 p-4 rounded-xl">
                                <p className="text-sm text-gray-400">Energy Delivered</p>
                                <p className="text-2xl font-bold">{selectedStation.totalEnergy} kWh</p>
                            </div>
                            <div className="bg-gray-900 p-4 rounded-xl">
                                <p className="text-sm text-gray-400">Avg. Session Duration</p>
                                <p className="text-2xl font-bold">{selectedStation.avgDuration} min</p>
                            </div>
                        </div>
                    </div>
                )}
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
