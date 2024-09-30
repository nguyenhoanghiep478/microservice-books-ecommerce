'use client'

import styles from '@/app/ui/admin/product/create/create.module.css'
import { useState } from 'react';
import useSWR from 'swr';
const fetcher = (url: string) => fetch(url).then((res) => res.json());
const fetchUrl = "http://localhost:8031/api/v1/book/create";
const categoryUrl = "http://localhost:8031/api/v1/category/get-all"
const token = localStorage.getItem('token')
const CreateProduct = () => {
    const {data,error} = useSWR(categoryUrl,fetcher);
    const [formData, setFormData] = useState({
        title: '',
        name: '',
        selectedCategory: '',
        price: '',
        quantity: '',
        description: '',
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
        form.append('selectedCategory', formData.selectedCategory);
        form.append('price', formData.price);
        form.append('quantity', formData.quantity);
        form.append('description', formData.description);
        

        const imageInput = e.currentTarget.elements.namedItem('image') as HTMLInputElement;
        if (imageInput && imageInput.files?.length) {
          form.append('image', imageInput.files[0]);
        }
    
        try {
          const response = await fetch(fetchUrl, {
            method: 'POST',
            headers:{
                'Authorization': `Bearer ${token}`
            },
            body: form,
          });
    
          if (!response.ok) {
            throw new Error('Failed to submit form');
          }
    
          alert('Product created successfully!');
        } catch (error) {
          console.error(error);
          alert('Failed to create product.');
        }
      };
   


    return (
        <div className={styles.container}>
            <form onSubmit={handleSubmit} className={styles.form}>
                <input type="text" placeholder='title' name='title' required onChange={handleChange}/>
                <input type="text" placeholder='name' name='name' required  onChange={handleChange}/>
                <select name="selectedCategory" id="selectedCategory" onChange={handleChange} >
                    <option value="general" >Choose a category</option>
                    {data?.result.map((category) => (
                         <option key={category.id} value={category.id}>
                             {category.name}
                        </option>
                    ))}
                </select>

                <input step="any" type="number" name="price" placeholder='price' required onChange={handleChange}/>
                <input type="number" name="quantity" placeholder='quantity' required onChange={handleChange}/>
                <input type="file" name="image" id="" required />
                
                <textarea
                    name="description"
                    id="description"
                    rows={5}
                    placeholder='description'
                >
                </textarea>
                <button type="submit" >Submit</button>
            </form>

        </div>
    )
}

export default CreateProduct;