// context/PermissionsContext.tsx
import { createContext, useContext, useState, ReactNode } from 'react';
import { Permission } from '@/type/permission.dt';

interface PermissionsContextType {
    permissions: Permission[];
    setPermissions: (permissions: Permission[]) => void;
}

const PermissionsContext = createContext<PermissionsContextType | undefined>(undefined);

export const PermissionsProvider = ({ children }: { children: ReactNode }) => {
    const [permissions, setPermissions] = useState<Permission[]>([]);

    return (
        <PermissionsContext.Provider value={{ permissions, setPermissions }}>
            {children}
        </PermissionsContext.Provider>
    );
};

export const usePermissions = () => {
    const context = useContext(PermissionsContext);
    if (context === undefined) {
        throw new Error('usePermissions must be used within a PermissionsProvider');
    }
    return context;
};
