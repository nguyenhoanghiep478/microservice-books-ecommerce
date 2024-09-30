'use client'

import styles from '@/app/ui/admin/user/singleUser/singleuser.module.css'
import { Book } from '@/type/dto/book.dt';
import Image from 'next/image'
import { usePathname } from 'next/navigation';
import { title } from 'process';
import { useState } from 'react';
import { Container, Spinner } from 'react-bootstrap';
import useSWR from 'swr';
const fetcher = (url: string) => fetch(url).then((res) => res.json());
const fetchUrl = "http://localhost:8031/api/v1/book/anonymous/get-by-id/";
const categoryUrl = "http://localhost:8031/api/v1/category/get-all"
const updateUrl = "http://localhost:8031/api/v1/book/update-book-by-id/"
const SingleProductPage = () => {
    const token = localStorage.getItem('token')
    const [updateError, setUpdateError] = useState('');
    const [isSuccess,setIsSuccess] = useState<Boolean>(true);
    const id = usePathname().split("/").pop();
    const response = useSWR(categoryUrl, fetcher);
    const { data, error } = useSWR(fetchUrl + id, fetcher);



    const [formData, setFormData] = useState({
        title: '',
        name: '',
        categoryId: '',
        price: '',
        availableQuantity: '',
        description: '',
        isInStock: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
        
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = new FormData();
        form.append('title', formData.title);
        form.append('name', formData.name);
        form.append('categoryId', formData.categoryId);
        form.append('price', formData.price);
        form.append('availableQuantity', formData.availableQuantity);
        form.append('description', formData.description);
        form.append('isInStock', formData.isInStock);
        const imageInput = e.currentTarget.elements.namedItem('image') as HTMLInputElement;
        if (imageInput && imageInput.files?.length) {
          form.append('image', imageInput.files[0]);
        }
        try {
            const updateResponse = await fetch(updateUrl+id, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: form
            })
            if (!updateResponse.ok) {
                const errorData:CustomError = await updateResponse.json();
                throw new Error(errorData.error ||'Login failed');
            }
            setFormData({
                title: '',
                name: '',
                categoryId: '',
                price: '',
                availableQuantity: '',
                description: '',
                isInStock: ''
            });
        } catch (error) {
            const errorMessage = (error as Error).message;
            setUpdateError(errorMessage);
        }
    }

    if (response.data == null) return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </Container>
    );

    if (data?.result == null) return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </Container>
    );

    const categories = response.data.result;
    const book: Product = data.result




    return (
        <div className={styles.container}>
            <div className={styles.infoContainer}>
                <div className={styles.imgContainer}>
                    <Image src={`data:image/jpeg;base64,${book?.image}`} alt='' fill />
                </div>
                {book.name}

                {updateError && <p className="text-danger">{updateError}</p>}
            </div>
            <div className={styles.formContainer}>
                <form onSubmit={handleSubmit} className={styles.form}>
                    <label >Title</label>
                    <input type="text" name='title' placeholder={`${book?.title}`} onChange={handleChange} value={formData.title} />
                    <label >Name</label>
                    <input type="text" name='name' placeholder={`${book?.name}`}  onChange={handleChange} value={formData.name} />
                    <label >Quantity</label>
                    <input type="number" name='availableQuantity' placeholder={`${book?.availableQuantity}`} onChange={handleChange} value={formData.availableQuantity} />
                    <label >Price</label>
                    <input step="any" type="number" name='price' placeholder={`${book?.price}`} onChange={handleChange} value={formData.price} />
                    <label >Category</label>
                    <select name="categoryId" id="selectedCategory" onChange={handleChange}>
                        {categories.map((category) => (
                            <option selected={category.id === book.categoryId} key={category.id} value={category.id}  >
                                {category.name}
                            </option>
                        ))}
                    </select>
                    <label >Image</label>
                    <input type="file" name="image" onChange={handleChange}/>
                    <label >Is in stock ?</label>
                    <select name="isInStock" onChange={handleChange}>
                        <option selected={book.isInStock === true} value="true">Yes</option>
                        <option selected={book.isInStock === false} value="false">No</option>
                    </select>
                    <label >Description</label>
                    <textarea typeof='text' name='description' placeholder={`${book?.description ? book.description : ""}`} onChange={handleChange}></textarea>

                    <button type="submit">Update</button>
                </form>
            </div>
        </div>
    )
}

export default SingleProductPage