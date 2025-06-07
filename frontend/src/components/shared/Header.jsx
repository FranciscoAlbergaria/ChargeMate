import React from 'react';
import Logo from '../../assets/Logo_ChargeMate.png';

export default function Header() {
    return (
        <header className="flex justify-center items-center p-4 bg-[#202025]">
            <img src={Logo} alt="ChargeMate Logo" className="h-10 w-auto" />
        </header>
    );
}
