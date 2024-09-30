'use client'

import { Inter } from "next/font/google";
import "../ui/globals.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import AppHeader from "@/app/ui/client/navbar/app.header";
import AppFooter from "../../component/app.footer";
import { useSWRConfig } from "swr";
import { Container } from "react-bootstrap";
import { CartProvider } from "../context/CartContext";
const inter = Inter({ subsets: ["latin"] });


export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const { refreshInterval, mutate, cache, ...restConfig } = useSWRConfig()

  return (
    <html lang="en">
      <body className={`d-flex flex-column min-vh-100`}>
        <div className="w-full h-screen flex flex-col">
            <AppHeader />
            <Container className="flex-grow-1">
              {children}
            </Container>
            {/* <AppFooter /> */}
        </div>
      </body>
    </html>
  );
}
