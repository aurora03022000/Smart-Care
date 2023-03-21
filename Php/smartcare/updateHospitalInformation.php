<?php
if(isset($_POST['hospital_name']) && isset($_POST['hospital_address']) && isset($_POST['hospital_email']) && isset($_POST['hospital_date_acquired'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $hospital_name = validate($_POST['hospital_name']);
    $hospital_address = validate($_POST['hospital_address']);
    $hospital_email = validate($_POST['hospital_email']);
    $hospital_date_acquired = validate($_POST['hospital_date_acquired']);

    $hospital_date_acquired = new DateTime($hospital_date_acquired);
    $hospital_date_acquiredFinal = $hospital_date_acquired->format('F d, Y');

    $sql = "UPDATE hospital_information SET hospital_name='$hospital_name', hospital_address = '$hospital_address', hospital_email = '$hospital_email',
    hospital_date_acquired = '$hospital_date_acquiredFinal'";
    $result = mysqli_query($conn, $sql);
    
    if($result){
        $getHospitalInformation = "SELECT * FROM hospital_information";
        $getHospitalInformationResult = mysqli_query($conn, $getHospitalInformation);

        if($getHospitalInformationResult){
            while($row = mysqli_fetch_assoc($getHospitalInformationResult)){
                echo $row['hospital_name']."`".$row['hospital_address']."`".$row['hospital_email']."`".$row['hospital_date_acquired'];
            }
        }

        else{
            echo 'failure';
        }
    }

    else{
        echo 'failure';
    }
    

}
?>