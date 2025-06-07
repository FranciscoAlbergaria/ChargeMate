import React from 'react';
import { Search, Settings } from 'lucide-react';

export default function SearchBar() {
    return (
        <div className="flex items-center bg-[#2A2A2E] rounded-xl px-4 py-2 mx-4 mt-2">
            <Search className="text-gray-400 mr-2" size={20} />
            <input
                type="text"
                placeholder="Enter a location or city"
                className="flex-1 bg-transparent text-white placeholder-gray-500 focus:outline-none"
            />
            <Settings className="text-gray-400 ml-2" size={20} />
        </div>
    );
}
