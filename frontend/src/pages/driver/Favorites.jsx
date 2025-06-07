import React from 'react';
import { useFavorites } from '../../context/FavoritesContext.jsx';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

export default function Favorites() {
    const { favorites } = useFavorites();

    return (
        <div className="min-h-screen bg-[#202025] text-white pb-20">
            <Header />
            <div className="p-5 space-y-4">
                <h1 className="text-2xl font-bold">My Favorites</h1>

                {favorites.length === 0 ? (
                    <p className="text-gray-500">You haven’t added any favorites yet.</p>
                ) : (
                    <ul className="space-y-3">
                        {favorites.map((s) => (
                            <li
                                key={s.id}
                                className="bg-gray-800 p-4 rounded-xl shadow-sm space-y-1"
                            >
                                <h3 className="text-lg font-semibold">{s.name}</h3>
                                <p className="text-sm text-gray-400">{s.address}</p>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
            <BottomNavEvDriver />
        </div>
    );
}
