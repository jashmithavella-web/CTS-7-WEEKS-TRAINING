import styles from '../Styles/CohortDetails.module.css';

function CohortDetails(props){

    const data = props.cohort;

    const headingStyle={

        color:data.status==="Ongoing"?"green":"blue"

    };

    return(

        <div className={styles.box}>

            <h3 style={headingStyle}>{data.status}</h3>

            <dl>

                <dt>Cohort Name</dt>
                <dd>{data.name}</dd>

                <dt>Mentor</dt>
                <dd>{data.mentor}</dd>

                <dt>Start Date</dt>
                <dd>{data.startDate}</dd>

            </dl>

        </div>

    );

}

export default CohortDetails;