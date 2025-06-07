import React from 'react';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

const sessions = [
    {
        id: 1,
        date: '2025-05-30 14:20',
        station: 'GreenCharge Central',
        duration: '42 min',
        energy: '18.5 kWh',
        cost: '€7.40',
    },
    {
        id: 2,
        date: '2025-05-28 18:10',
        station: 'ChargePoint Norte',
        duration: '30 min',
        energy: '12.3 kWh',
        cost: '€5.10',
    },
    {
        id: 3,
        date: '2025-05-26 09:15',
        station: 'EVZone Sul',
        duration: '54 min',
        energy: '22.7 kWh',
        cost: '€9.10',
    },
];

export default function ChargingHistory() {
    return (
        <div className="min-h-screen bg-[#202025] text-white pb-24">
            <Header title="Charging History" />

            <div className="p-4 space-y-4">
                {sessions.map(session => (
                    <div
                        key={session.id}
                        className="bg-gray-800 p-4 rounded-xl shadow space-y-1"
                    >
                        <p className="text-sm text-gray-400">{session.date}</p>
                        <h3 className="text-lg font-semibold">{session.station}</h3>
                        <div className="flex justify-between text-sm text-gray-300">
                            <span>Duration: {session.duration}</span>
                            <span>Energy: {session.energy}</span>
                            <span>Cost: {session.cost}</span>
                        </div>
                    </div>
                ))}
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
