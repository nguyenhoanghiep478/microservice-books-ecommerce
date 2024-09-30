import useSWR from "swr";

export class OrderClient {
    private url = "http://localhost:8031/api/v1/order";
    private token = localStorage.getItem('token');
    private fetcher = (url: string) => fetch(url, {
        headers: {
            'Authorization': `Bearer ${this.token}`,
            'Content-Type': 'application/json'
        }
    }).then((res) => res.json());


    public statePostClient = async (endPoint: string, body) => {
        try {
            const response = await fetch(this.url + endPoint, {
                headers: {
                    'Authorization': `Bearer ${this.token}`,
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: body
            })

            if (!response.ok) {
                const errorData: CustomError = await response.json();

                throw new Error(errorData.error || 'Login failed');
            }

            const data = await response.json();

            return data;

        } catch (error) {
            const errorMessage = (error as Error).message;
            console.log(errorMessage)
        }
    }


    public stateGetClient = (endPoint: string) => {
        const { data, error } = useSWR(this.url + endPoint, this.fetcher);

        if (error) {
            console.log(error.message)
        }

        return data;
    }
}