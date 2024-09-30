"use client"

import { OrderClient } from '@/app/state/orders.state';
import styles from './chart.module.css'
import { LineChart, Line, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { DateOfWeek } from '@/type/dto/date-of-week.enum';
import { ChartOrder } from '@/type/dto/chart-order.dt';


const Chart = () => {
    const orderClient: OrderClient = new OrderClient();
    const getDayOfWeek = (date: number): string => {
        return DateOfWeek[date];
    }


    const createChart = (data: any[][]): ChartOrder[] => {
        return Object.keys(DateOfWeek)
            .filter(key => isNaN(Number(key)))
            .map(date => {
                const dataOfDay = data.find(item => getDayOfWeek(item[0]) === date)
                    const value = dataOfDay ? dataOfDay[1] : 0;
                    return new ChartOrder(date, value)
            })
    }
    const chartData = orderClient.stateGetClient('/get-chart-order-in-week')?.result as [][];


    if (!chartData) {
        return;
    }
    const chart = createChart(chartData);

    console.log(chart)

    return (
        <div className={styles.container}>
            <h2>Weekly Recap</h2>
            <ResponsiveContainer width="100%" height="100%">
                <LineChart
                    width={500}
                    height={300}
                    data={chart}
                    margin={{
                        top: 5,
                        right: 30,
                        left: 20,
                        bottom: 5,
                    }}
                >
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip contentStyle={{ background: "#151c2c", border: "none" }} />
                    <Legend />
                    <Line type="monotone" dataKey="totalPrice" stroke="#8884d8" strokeDasharray="5 5" />
                    {/* <Line type="monotone" dataKey="click" stroke="#82ca9d" strokeDasharray="3 4 5 2" /> */}
                </LineChart>
            </ResponsiveContainer>
        </div>
    )
}

export default Chart;