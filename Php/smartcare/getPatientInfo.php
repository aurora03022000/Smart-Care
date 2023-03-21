<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM patient WHERE encoder='$currentUserID' and isExist=1 ORDER BY patient_name";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){

            if($row['share_id'] == 0){
                echo $row['patient_name']."`".$row['patient_id']."`".$row['room']."`".$row['share_id']."`"."noname"."%";
            }

            else{
                $sql1 = "SELECT * FROM user WHERE user_id='$row[share_id]'";
                $result1 = $conn->query($sql1);
    
                if($result1){
                    while($row1 = mysqli_fetch_assoc($result1)){
                        echo $row['patient_name']."`".$row['patient_id']."`".$row['room']."`".$row['share_id']."`".$row1['staff_name']."%";
                    }
                }
    
                else{
                    echo 'failure';
                }
            }

        }
    }

    else{
        echo 'failure';
    }

}
?>