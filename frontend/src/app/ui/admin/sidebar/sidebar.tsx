'use client'
import styles from './sidebar.module.css'

import {
    MdDashboard,
    MdSupervisedUserCircle,
    MdShoppingBag,
    MdWork,
    MdAnalytics,
    MdPeople,
    MdOutlineSettings,
    MdHelpCenter,
    MdLogout,
} from "react-icons/md"
import { FaBullhorn } from "react-icons/fa";
import MenuLink from './menu-link/menuLink';
import Image from 'next/image';
import { logout } from '@/app/state/logout';
import { useAuth } from '@/app/context/AuthContext';
import { useRouter } from 'next/navigation';

const Sidebar = () => {
    const { isAuthenticated, setIsAuthenticated } = useAuth();
    const route = useRouter()

    const menuItem = [
        {
            title: "Pages",
            list: [
                {
                    title: "Dashboard",
                    path: "/dashboard",
                    icon: <MdDashboard />
                },
                {
                    title: "User",
                    path: "/dashboard/user",
                    icon: <MdSupervisedUserCircle />
                },
                {
                    title: "Product",
                    path: "/dashboard/product",
                    icon: <MdShoppingBag />
                },
                {
                    title: "Marketing",
                    path: "/dashboard/marketing",
                    icon: <FaBullhorn />
                }
            ]
        },
        {
            title: "Analytics",
            list: [
                {
                    title: "Revenue",
                    path: "/dashboard/revenue",
                    icon: <MdWork />
                },
                {
                    title: "Reports",
                    path: "/dashboard/report",
                    icon: <MdAnalytics />
                },
                {
                    title: "Team",
                    path: "/dashboard/team",
                    icon: <MdPeople />
                }
            ]
        },
        {
            title: "User",
            list: [
                {
                    title: "Setting",
                    path: "/dashboard/setting",
                    icon: <MdOutlineSettings />
                },
                {
                    title: "Help",
                    path: "/dashboard/help",
                    icon: <MdHelpCenter />
                }
            ]
        }
    ]




    return (<>

        <div className={styles.container}>
            <div className={styles.user}>
                <Image className={styles.userImage} src="/noavatar.png" alt='' width="50" height='50'></Image>
                <div className={styles.userDetails}>
                    <span className={styles.userName}>Hiep</span>
                    <span className={styles.userTitle}>Administrator</span>
                </div>
            </div>
            <ul className={styles.list}>
                {menuItem.map(item => (
                    <li key={item.title}>
                        <span className={styles.item}>{item.title}</span>
                        {item.list.map(listItem => (
                            <MenuLink item={listItem} key={listItem.title}></MenuLink>
                        ))}
                    </li>
                ))}
                <button className={styles.logout} onClick={() => logout(setIsAuthenticated,route)}>
                    <MdLogout />
                    Logout
                </button>
            </ul>

        </div>
    </>

    )
}
export default Sidebar;