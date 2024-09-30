

import { Inter } from "next/font/google";
import "../ui/globals.css";
import Navbar from "../ui/admin/navbar/navbar";
import styles from '../ui/admin/dashboard.module.css'
import Sidebar from "../ui/admin/sidebar/sidebar";
import Footer from "../ui/admin/footer/footer";
const inter = Inter({ subsets: ["latin"] });


export default function AdminLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${inter.className} d-flex flex-column min-vh-100`}>
        <div className={styles.container}>
          <div className={styles.menu}>
            <Sidebar />
          </div>
          <div className={styles.content}>
            <Navbar />
            {children}
            <Footer/>
          </div>
        </div>
      </body>
    </html>
  );
}
