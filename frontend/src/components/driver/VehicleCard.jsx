import React from 'react';

const stations = [
    {
        name: 'Rockafella Center',
        address: 'SF, CA 94116',
        status: 'Occupied',
        statusColor: 'text-red-500',
    },
    {
        name: 'Blastix Car Center',
        address: 'SF, CA 94115',
        status: 'Maintenance',
        statusColor: 'text-gray-400',
    },
];

export default function VehicleCard() {
    return (
        <div className="bg-[#1E1E22] rounded-xl overflow-hidden w-full max-w-md mx-auto shadow-lg">
            {/* Header com modelo e imagem */}
            <div className="bg-gradient-to-r from-cyan-400 to-blue-500 p-4 flex justify-between items-center">
                <span className="text-black font-bold uppercase text-sm">Tesla Model 3</span>
                <img
                    src="https://tesla-cdn.thron.com/delivery/public/image/tesla/f78dbdde-f84a-496b-baa1-2fbb7a7b8fb4/bvlatuR/std/2880x1800/_25-Hero-D"
                    alt="Tesla"
                    className="h-12 object-contain"
                />
            </div>

            {/* Lista de estações */}
            <div className="p-4 space-y-3 text-sm">
                {stations.map((station, index) => (
                    <div key={index} className="border-t border-gray-700 pt-3">
                        <p className="text-white font-medium">{station.name}</p>
                        <p className="text-gray-400">{station.address}</p>
                        <div className="flex justify-between items-center mt-1">
                            <a href="#" className="text-cyan-400 underline">
                                Get Directions
                            </a>
                            <span className={station.statusColor}>{station.status}</span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
