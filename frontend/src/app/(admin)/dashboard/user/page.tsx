'use client'

import Pagination from '@/app/ui/admin/pagination/pagination';
import Search from '@/app/ui/admin/search/search';
import styles from '@/app/ui/admin/user/user.module.css'
import Image from 'next/image';
import Link from 'next/link';
import { useState } from 'react';

const User = () => {
   const [page,setPage] = useState(1);


    return (
        <div className={styles.container}>
            <div className={styles.top}>
                <Search placeholder="Search for a user..." />
                <Link href="/dashboard/user/create">
                    <button className={styles.addButton}>Add New</button>
                </Link>
            </div>
            <table className={styles.table}>
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Email</td>
                        <td>Create At</td>
                        <td>Role</td>
                        <td>Status</td>
                        <td>Action</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <div className={styles.user}>
                                <Image className={styles.userImage} src="/noavatar.png" alt='' width={40} height={40} />
                            </div>
                        </td>
                        <td>
                            nguyenhoanghiep478@gmail.com
                        </td>
                        <td>
                            20.08.2024
                        </td>
                        <td>
                            Admin
                        </td>
                        <td>
                            active
                        </td>
                        <td>
                            <div className={styles.buttons}>

                                <Link href="/dashboard/user/test">
                                    <button className={`${styles.button} ${styles.viewButton}`}>View</button>
                                </Link>
                                <button className={`${styles.button} ${styles.deleteButton}`}>Block</button>
                            </div>
                        </td>
                    </tr>
                </tbody>

            </table>
            <Pagination totalPages={1} currentPage={1} onPageChange={(page) => setPage(page)}/>
        </div>
    )
}

export default User;