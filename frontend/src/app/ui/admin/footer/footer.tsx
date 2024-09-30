import styles from './footer.module.css'

const Footer = ()=>{
    return(
        <div className={styles.container}>
            <div className={styles.logo}>Dev</div>
            <div className={styles.text}>Hiep</div>
        </div>
    )
}

export default Footer;