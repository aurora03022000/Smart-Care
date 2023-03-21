<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    
    if($selectedValue == "Activity"){
        if($searchValue == "All Activity"){
            $sql1 = "SELECT * FROM history_log";
            $result1 = $conn->query($sql1); 
        }

        else if($searchValue == "Admitted Patient"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Admitted a patient%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1); 
        }

        else if($searchValue == "Dismissed Patient"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Dismissed patient%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Updated Patient Health Records"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Updated patient health records%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }
        
        else if($searchValue == "Shared Patient"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Shared a patient%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Stopped Share Patient"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Stopped patient sharing%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Updated Medical Staff Information"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Updated account information%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Updated Patient Information"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Updated patient information%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Deleted Staff"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Deleted Staff%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Deleted Device"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Removed device%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }

        else if($searchValue == "Logged in"){
            $sql1 = "SELECT * FROM history_log WHERE activity LIKE '%Logged in%' ORDER BY `dateStamp` ASC";
            $result1 = $conn->query($sql1);
        }
    }
    
    //tandaan ni
    else if($selectedValue == "Medical Staff Name"){
        $sql1 = "SELECT * FROM user WHERE staff_name LIKE '%$searchValue%'";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Activity Date"){
        $activityDate = new DateTime($searchValue);
        $activityDateFinal = $activityDate->format('F d, Y');

        $sql1 = "SELECT * FROM history_log WHERE dateStamp = '$activityDateFinal'";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Activity Time"){
        $firstTwoString = substr($searchValue, 0, 2);//kwaon ang first wo value sang input
        $getAMorPM = substr($searchValue, 6);//tapos kwaon naman ang last two letters sang input

        $searchValue = $firstTwoString;

        $sql1 = "SELECT * FROM history_log WHERE timeStamp LIKE '%$searchValue%'";
        $result1 = $conn->query($sql1); 
    }

    if($result1){
        
        if($selectedValue == "Activity"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM user WHERE user_id = '$row[user]'";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row['history_id'].'`'.$row1['staff_name'].'`'.$row['dateStamp'].'`'.$row['timeStamp'].'`'.$row['activity'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else if($selectedValue == "Activity Date"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM user WHERE user_id = '$row[user]'";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row['history_id'].'`'.$row1['staff_name'].'`'.$row['dateStamp'].'`'.$row['timeStamp'].'`'.$row['activity'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else if($selectedValue == "Medical Staff Name"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM history_log WHERE user = '$row[user_id]'";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row1['history_id'].'`'.$row['staff_name'].'`'.$row1['dateStamp'].'`'.$row1['timeStamp'].'`'.$row1['activity'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else{
            while($row2 = mysqli_fetch_assoc($result1)){
                $timeStamp = $row2['timeStamp'];
                $getAMorPM1 = substr($timeStamp, 6); 

                if($getAMorPM == $getAMorPM1){
                    $sql2 = "SELECT * FROM user WHERE user_id = '$row2[user]'";
                    $result2 = $conn->query($sql2); 

                    if($result2){
                        while($row1 = mysqli_fetch_assoc($result2)){
                            echo $row2['history_id'].'`'.$row1['staff_name'].'`'.$row2['dateStamp'].'`'.$row2['timeStamp'].'`'.$row2['activity'].'`';
                        }
                    }

                    else{
                        echo 'failure';
                    }
                }
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>