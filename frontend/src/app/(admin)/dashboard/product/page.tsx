'use client'

import Pagination from '@/app/ui/admin/pagination/pagination';
import styles from '@/app/ui/admin/product/product.module.css'
import Search from '@/app/ui/admin/search/search';
import Image from 'next/image';
import Link from 'next/link';
import { useState } from 'react';
import useSWR from 'swr';
const fetcher = (url: string) => fetch(url).then((res) => res.json());
const bookUrl = 'http://localhost:8031/api/v1/book/anonymous/get-all'
const Product = () =>{
    const { data, error } = useSWR('http://localhost:8031/api/v1/book/anonymous/get-all', fetcher);
    const [currentPage, setCurrentPage] = useState(1);
    
    const books:Product[] = data?.result;
    const itemsPerPage = 10;
    const currentBooks = books?.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
    books?.sort((a, b) => {
        const nameA = a.name.replace(/\d+$/, '').trim();
        const nameB = b.name.replace(/\d+$/, '').trim();
      
        const nameComparison = nameA.localeCompare(nameB);
        
        if (nameComparison !== 0) {
          return nameComparison;
        }
      
        const numberA = a.name.match(/\d+$/);
        const numberB = b.name.match(/\d+$/);
      
        if (numberA && numberB) {
          return parseInt(numberA[0]) - parseInt(numberB[0]);
        }
      
        if (!numberA) return -1;
        if (!numberB) return 1;
      
        return 0;
    });

    const totalPages = Math.ceil(books?.length/ itemsPerPage) || 1;

    const formatDate = (dateString: string): string => {
        const date = new Date(dateString);
      
        if (isNaN(date.getTime())) {
          throw new Error('Invalid date format');
        }
      
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
      
        return `${day}.${month}.${year}`;
      }

    return (
       
        <div className={styles.container}>
            <div className={styles.top}>
                <Search placeholder="Search for a product..." />
                <Link href="/dashboard/product/create">
                    <button className={styles.addButton}>Add New</button>
                </Link>
            </div>
            <table className={styles.table}>
                <thead>
                    <tr>
                        <td>Image</td>
                        <td>Desciption</td>
                        <td>Price</td>
                        <td>Create At</td>
                        <td>Stock</td>
                        <td>Action</td>
                    </tr>
                </thead>
                <tbody>
                    {currentBooks?.map((book:Product) => (
                        <tr>
                        <td>
                            <div className={styles.product}>
                                <Image className={styles.productImage} src={`data:image/jpeg;base64,${book.image}`} alt='' width={40} height={40} />
                            </div>
                        </td>
                        <td>
                            {book.name}
                        </td>
                        <td>
                            {book.price}
                        </td>
                        <td>
                            {formatDate(book.createdDate)}
                        </td>
                        <td>
                            {book.availableQuantity}
                        </td>
                        <td>
                            <div className={styles.buttons}>

                                <Link href={`/dashboard/product/${book.id}`}>
                                    <button className={`${styles.button} ${styles.viewButton}`}>View</button>
                                </Link>
                                <button className={`${styles.button} ${styles.deleteButton}`}>Delete</button>
                            </div>
                        </td>
                    </tr>
                    ))}
                </tbody>

            </table>
            <Pagination 
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={(page) => setCurrentPage(page)}
            ></Pagination>
        </div>
    )
}

export default Product;