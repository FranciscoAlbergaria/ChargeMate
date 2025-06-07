import { createContext, useContext, useState } from 'react';

const FavoritesContext = createContext();

export function FavoritesProvider({ children }) {
    const [favorites, setFavorites] = useState([]);

    const addFavorite = (station) => {
        if (!favorites.some((fav) => fav.id === station.id)) {
            setFavorites((prev) => [...prev, station]);
        }
    };

    const isFavorite = (stationId) => {
        return favorites.some((s) => s.id === stationId);
    };

    return (
        <FavoritesContext.Provider value={{ favorites, addFavorite, isFavorite }}>
            {children}
        </FavoritesContext.Provider>
    );
}

export function useFavorites() {
    return useContext(FavoritesContext);
}
