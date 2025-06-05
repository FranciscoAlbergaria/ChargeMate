import React from 'react';
import Logo from '../../assets/Logo_ChargeMate.png'; // Importa o logo
import { Menu } from 'lucide-react'; // Ícone de menu (Lucide)
import UserIcon from '../../assets/user-placeholder.png'; // Placeholder para foto de perfil

export default function Header() {
    return (
        <header className="flex justify-between items-center p-4 bg-[#202025]">
            {/* Ícone do menu */}
            <button className="text-white">
                <Menu size={24} />
            </button>

            {/* Logo */}
            <img src={Logo} alt="ChargeMate Logo" className="h-10 w-auto" />

            {/* Ícone do utilizador */}
            <img
                src={UserIcon}
                alt="User Profile"
                className="h-8 w-8 rounded-full object-cover border border-gray-500"
            />
        </header>
    );
}
