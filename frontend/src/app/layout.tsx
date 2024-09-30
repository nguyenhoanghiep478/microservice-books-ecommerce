'use client'

import 'bootstrap/dist/css/bootstrap.min.css';
import { useSWRConfig } from "swr";
import { AuthProvider } from "./context/AuthContext";


export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const { refreshInterval, mutate, cache, ...restConfig } = useSWRConfig()

  return (
    <html>
      <body>
        <AuthProvider>
          {children}
        </AuthProvider>
      </body>
    </html>
  );
}
