'use client'

import { usePathname } from "next/navigation";
import { Container, Spinner } from "react-bootstrap";
import styles from '@/app/ui/client/singleProduct/singleProduct.module.css'
import useSWR from "swr";
import Image from "next/image";

const fetcher = (url: string) => fetch(url).then((res) => res.json());
const fetchUrl = "http://localhost:8031/api/v1/book/anonymous/get-by-id/";

const SingleProductClientPage = () => {
    const id = usePathname().split("/").pop();
    const { data, error } = useSWR(fetchUrl + id, fetcher);

    if (data?.result == null) return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </Container>
    );

    const book: Product = data.result

    return (
        <div className={styles.container}>
            <div className={styles.infoContainer}>
                <div className={styles.imgContainer}>
                    <Image src={`data:image/jpeg;base64,${book?.image}`} alt='' fill />
                </div>
                <h2>{book.name}</h2>


            </div>
            <div>
                <div className={styles.details}>
                    <p><strong>Title: </strong>{book?.title}</p>
                    <p><strong>Name: </strong>{book?.name}</p>
                    <p><strong>Quantity: </strong>{book?.availableQuantity}</p>
                    <p><strong>Price: </strong>${book?.price}</p>
                    <p><strong>Category: </strong>{book?.categoryId}</p>
                    <p><strong>In Stock: </strong>{book?.isInStock ? 'Yes' : 'No'}</p>
                    <p><strong>Description: </strong>{book?.description}</p>
                </div>
            </div>
        </div>
    )
}

export default SingleProductClientPage;
