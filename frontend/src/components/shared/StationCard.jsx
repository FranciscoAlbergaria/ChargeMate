import React from 'react';

import { useFavorites } from '../../context/FavoritesContext.jsx';

export default function StationCard({ address, onBook, onClose, station }) {
    const { addFavorite, isFavorite } = useFavorites();

    return (
        <div className="bg-gray-800 p-4 rounded-xl shadow-lg space-y-2">
            <div className="flex justify-between">
                <h2 className="text-xl font-semibold">{station.name}</h2>
                <button onClick={onClose}>✕</button>
            </div>
            <p className="text-sm text-gray-400">{address}</p>

            <button className="btn btn-primary w-full" onClick={onBook}>
                Book Station
            </button>

            <button
                className={`btn w-full ${
                    isFavorite(station.id) ? 'btn-success' : 'btn-outline'
                }`}
                onClick={() => addFavorite(station)}
            >
                {isFavorite(station.id) ? 'Favorited' : 'Add to Favorites'}
            </button>
        </div>
    );
}

