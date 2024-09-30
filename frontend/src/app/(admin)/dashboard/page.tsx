import Card from '@/app/ui/admin/card/card';
import styles from '../../ui/admin/dashboard.module.css'
import Rightbar from '@/app/ui/admin/right bar/rightbar';
import Transactions from '@/app/ui/admin/transactions/transactions';
import Chart from '@/app/ui/admin/chart/chart';


const Dashboard = () => {
  
  return (
    <div className={styles.wrapper}>
      <div className={styles.main}>
        <div className={styles.cards}>
          <Card totalUser = {'10.200'} title={"Total User"}/>
          <Card totalUser = {'1.200'} title={"Total User"}/>
          <Card totalUser = {'1.300'} title={"Total User"}/>
        </div>
        <Transactions/>
        <Chart/>
      </div>
      <div className={styles.side}>
        <Rightbar/>
      </div>
    </div>
  );
};


export default Dashboard;