import { MdSupervisedUserCircle } from 'react-icons/md';
import styles from './card.module.css'

interface CardProps{
    totalUser : string;
    title: string
}


const Card:React.FC<CardProps> = ({totalUser,title}) => {
    return (
        <div className={styles.container}>
            <MdSupervisedUserCircle size={24} />
            <div className={styles.texts}>
                <span className={styles.title} >{title}</span>
                <span className={styles.number}>{totalUser}</span>
                <span className={styles.detail}>
                    <span className={styles.positive}>12%</span> more than last week
                </span>
            </div>
        </div>
    )
}
export default Card;