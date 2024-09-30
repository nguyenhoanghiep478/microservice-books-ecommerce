'use client'
import Image from 'next/image';
import styles from './transactions.module.css';
import useSWR from 'swr';
import { Order } from '@/type/dto/order.dt';
import { Status } from '@/type/status.dt';



const ORDER_URL = "http://localhost:8031/api/v1/order";
const AMOUNT = 5;
const Transactions = () => {
    const token = localStorage.getItem("token");
    const fetcher = (url: string) => fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then((res) => res.json());
    const { data, error } = useSWR(ORDER_URL + "/get-latest" + `/${AMOUNT}`, fetcher)
    const transactions: Order[] = data?.result;

    const parseDate = (timestamp: string) => {
        let date = new Date(timestamp);

        let day = date.getDate().toString().padStart(2, '0');
        let month = (date.getMonth() + 1).toString().padStart(2, '0'); 
        let year = date.getFullYear();

        return  `${day}.${month}.${year}`;
    }


    const getStatusStyle = (status:string) =>{
        switch(status){
            case Status.PENDING :
                return styles.pending
            case Status.COMPLETED:
                return styles.done
            case Status.CANCELLED:
                return styles.cancelled
        }
    }


    if(transactions == null){
        return ;
    }


    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Lastest Transactions</h2>
            <table className={styles.table}>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Amount</th>
                    </tr>
                </thead>
                <tbody>
                    {transactions.map((transaction) => (
                        <tr key={transaction.orderNumber}>
                            <td>
                                <div className={styles.user}>
                                    <Image
                                        src="/noavatar.png"
                                        alt=''
                                        width={40}
                                        height={40}
                                        className={styles.userImage}
                                    />
                                    {transaction.customerName}
                                </div>
                            </td>
                            <td><span className={`${styles.status} ${getStatusStyle(transaction.status)}`}>{transaction.status}</span> </td>
                            <td>{parseDate(transaction.createdDate)}</td>
                            <td>${transaction.totalPrice.toFixed(2)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default Transactions;