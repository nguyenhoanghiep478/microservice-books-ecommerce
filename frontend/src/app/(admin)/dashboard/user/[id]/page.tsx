import styles from '@/app/ui/admin/product/singleProduct/singleProduct.module.css'
import Image from 'next/image'
import { usePathname, useRouter } from 'next/navigation'

const SingleUserPage = () => {
   

    return (
        <div className={styles.container}>
            <div className={styles.infoContainer}>
                <div className={styles.imgContainer}>
                    <Image src="/noavatar.png" alt='' fill />
                </div>
                Hiep
            </div>
            <div className={styles.formContainer}>
                <form action="" className={styles.form}>
                    <label >Title</label>
                    <input type="email" name='email' placeholder='Hiep@gmail.com' />
                    <label >password</label>
                    <input type="text" name='password' />
                    <label >First Name</label>
                    <input type="text" name='first name' placeholder='Hiep' />
                    <label >Last Name</label>
                    <input type="text" name='last name' placeholder='Nguyen' />
                    <label >Phone</label>
                    <input type="phone" name='phone' placeholder='0951231232' />
                    <label >Is Active?</label>
                    <select name="isActive" id="isActive">
                        <option value="{true}">Yes</option>
                        <option value="{false}">No</option>
                    </select>
                    <label >Adress</label>
                    <textarea typeof='text' name='address' placeholder='HCM'></textarea>
                
                    <button >Update</button>
                </form>
            </div>
        </div>
    )
}

export default SingleUserPage