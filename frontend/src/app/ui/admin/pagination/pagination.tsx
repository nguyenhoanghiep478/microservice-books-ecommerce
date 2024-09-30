import styles from './pagination.module.css'

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void
}

const Pagination = ({ currentPage, totalPages, onPageChange }: PaginationProps) => {
    const handlePrevious = () => {
        if (currentPage > 1) {
            onPageChange(currentPage - 1);
        }
    };

    const handleNext = () => {
        if (currentPage < totalPages) {
            onPageChange(currentPage + 1);
        }
    };

    return (
        <div className={styles.container}>
            <button
                className={styles.button}
                onClick={handlePrevious}
                disabled={currentPage === 1}
            >
                Previous
            </button>
           
            <button
                className={styles.button}
                onClick={handleNext}
                disabled={currentPage === totalPages}
            >
                Next
            </button>
        </div>
    );
}

export default Pagination;