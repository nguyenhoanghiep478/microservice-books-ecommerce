

import styles from '@/app/ui/admin/user/create/create.module.css'

const CreateUser = () => {
    return (
        <div className={styles.container}>
            <form action="" className={styles.form}>
                <input type="email" placeholder='email' name='email' required />
                <input type="text" placeholder='password' name='password' required  />
                <input type="text" placeholder='first name' name='first name' required  />
                <input type="text" placeholder='last name' name='last name' required  />
                <input type="phone" placeholder='phone' name='phone' />
                <select name="category" id="caterogy">
                    <option value="general" >role</option>
                    <option value="opt1"> op1</option>
                    <option value="opt2"> op2</option>
                    <option value="opt3"> op3</option>
                    <option value="opt4"> op4</option>
                </select>
                <select name="isActive?" id="caterogy">
                    <option value="general" >Is active ?</option>
                    <option value="opt1"> op1</option>
                    <option value="opt2"> op2</option>
                    <option value="opt3"> op3</option>
                    <option value="opt4"> op4</option>
                </select>
                <input type="file" name="image" id='image'/>
                
                <textarea
                    name="address"
                    id="description"
                    rows={5}
                    placeholder='Address'
                >
                </textarea>
                <button type="submit">Submit</button>
            </form>

        </div>
    )
}

export default CreateUser;