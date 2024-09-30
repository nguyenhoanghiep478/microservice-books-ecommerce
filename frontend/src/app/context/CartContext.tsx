// CartContext.tsx
import { createContext, useContext, useRef, ReactNode } from 'react';

interface CartContextType {
    cartRef: React.RefObject<HTMLButtonElement>;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const cartRef = useRef<HTMLButtonElement>(null);

    return (
        <CartContext.Provider value={{ cartRef }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = () => {
    const context = useContext(CartContext);
    if (context === undefined) {
        throw new Error('useCartContext must be used within a CartProvider');
    }
    return context;
};
