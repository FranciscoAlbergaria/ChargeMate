import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

export default function Booking() {
    const location = useLocation();
    const [selectedStation, setSelectedStation] = useState(null);
    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        if (location.state?.station) {
            setSelectedStation(location.state.station);
        }
    }, [location.state]);

    const handleBooking = () => {
        const newBooking = {
            id: Date.now(),
            station: selectedStation.name,
            time: new Date().toLocaleString(),
            status: 'Confirmed',
        };

        setBookings((prev) => [...prev, newBooking]);

        // Marca estação como indisponível
        setStations((prev) =>
            prev.map((s) =>
                s.id === selectedStation.id ? { ...s, status: 'unavailable' } : s
            )
        );

        setSelectedStation(null);
    };


    return (
        <div className="bg-[#202025] text-white min-h-screen pb-20">
            <Header />

            <div className="p-5 space-y-6">
                <h1 className="text-2xl font-bold">Bookings</h1>

                {selectedStation && (
                    <div className="bg-gray-800 p-4 rounded-xl">
                        <h2 className="text-lg font-semibold mb-2">Book: {selectedStation.name}</h2>
                        <p className="text-sm text-gray-400">{selectedStation.address}</p>
                        <button
                            className="mt-4 btn btn-primary w-full"
                            onClick={handleBooking}
                        >
                            Confirm booking
                        </button>
                    </div>
                )}

                <div>
                    <h2 className="text-lg font-semibold mb-2">My bookings</h2>
                    {bookings.length === 0 ? (
                        <p className="text-gray-500">You don't have any bookings.</p>
                    ) : (
                        <ul className="space-y-3">
                            {bookings.map((b) => (
                                <li key={b.id} className="bg-gray-800 p-4 rounded-xl">
                                    <h3 className="text-md font-semibold">{b.station}</h3>
                                    <p className="text-sm text-gray-400">{b.time}</p>
                                    <p className="text-sm text-green-400">{b.status}</p>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
