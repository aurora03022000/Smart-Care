   M S B A M L   `   `   `  ����E  ASmart Care, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:Smart_Care
Smart_Care  5   	       Smart_Care.RegisterStaff   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:Smart_Care     5   	     � Title$  Register Staff��5   	   $��522.592��6    $��522.592��61   $��426.805��6E    � 
ResizeMode$ NoResize=�6U    � WindowStartupLocation$ CenterScreen=�6k   .��5	      � .+�5
   
   � $��39,0,25.8,30.8q�6   $��Bottom=�6'   $��Center=�6B   $	��356��6_   $	��450��6k   .+�5      �� $��	staff.png��6   $
��Left=�6'   $	��Top=�6B   $��44��6Z   $��52,6,0,0q�6f   $��46��6x   $
��Fill=�6�   �� 5      $��Register Medical Staff��6   $��	94,14,0,0q�65   $	��Top=�6H   $��20(�6`   $��Century Gothic)�6n   $
��Left=�6�   �� 5      $��Account Credentials��6   $��Italic$�62   $��Century Gothic)�6E   $��10(�6a   $	��Top=�6o   $
��Left=�6�   $��8,77,0,0q�6�   � 5      
�������$��1��6)   $��Center=�64   $��8,97,0,350.8q�6O   �� 5      $��
Username :��6   $��12(�6)   $
��Left=�67   $��
13,105,0,0q�6R   $	��Top=�6f   $��Century Gothic)�6~   $��78��6�   $��28��6�   $
��Bold"�6�   �� 5      $��
Password :��6   $
��Left=�6)   $��
12,140,0,0q�6D   $	��Top=�6X   $��Century Gothic)�6p   $
��Bold"�6�   �� 5      $��Name :��6   $
��Left=�6%   $��
13,223,0,0q�6@   $	��Top=�6T   $��Century Gothic)�6l   $
��Bold"�6�   �� 5      $��Birthdate :��6   $
��Left=�6*   $��
11,291,0,0q�6E   $	��Top=�6Y   $��Century Gothic)�6q   $
��Bold"�6�   �� 5      $��Gender :��6   $
��Left=�6'   $��
12,256,0,0q�6B   $	��Top=�6V   $��Century Gothic)�6n   $
��Bold"�6�   �� 5      $��	Address :��6   $
��Left=�6(   $��	8,325,0,0q�6C   $	��Top=�6V   $��Century Gothic)�6n   $
��Bold"�6�   �� 5      $��Number :��6   $
��Left=�6'   $��
10,358,0,0q�6B   $	��Top=�6V   $��Century Gothic)�6n   $
��Bold"�6�   �� 5      -   5         ��Name$ username��5      $
��Left=�6(   $��28��6C   $��
96,104,0,0q�6O    �� TextWrapping$
 Wrap=�6c   $	��Top=�6w   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   G� 5      -   5         G�Name$ password��5   6   $��Century Gothic)�6   $
��Left=�6H   $��
96,138,0,0q�6c   $	��Top=�6w   $	��250��6�   $��28��6�   $��3,5,0,0q�6�   �� 5      -   5        $
 name��5      $
��Left=�6$   $��28��6?   $��
96,221,0,0q�6K   $
 Wrap=�6_   $	��Top=�6s   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   �� 5      -   5         ��Name$ gender��5   A    � Cursor$
 Hand��6   $��Century Gothic)�6%   $
��Left=�6Q   $	��Top=�6l   $��
96,254,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   .@�5      �� .��6   Male6%   �� 5      .��6   Female6'   5      (  "System.Windows.Controls.DatePicker  5      -   5          Name$ bdate��5   5   $��Century Gothic)�6   $
��Left=�6D   $��
96,289,0,0q�6_   $	��Top=�6s   $��28��6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ address��5   2   $��Century Gothic)�6   $
��Left=�6C   $��28��6^   $��
96,323,0,0q�6j   $
 Wrap=�6~   $	��Top=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ number��5   [   $��Century Gothic)�6   $
��Left=�6k   $��28��6�   $��
96,356,0,0q�6�   $
 Wrap=�6�   $	��Top=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5       -   5        	 ��Name$	 RegisterStaff_Button��5    g   $
 Hand��6   ���#0EB021
�������$��Century Gothic)�6K   $
��Left=�6�   $��112,415,0,0q�6�   $	��173��6�   $��32��6�   $	��Top=�6�   
 � 	Resources
 5!       �� 
TargetType �� Property �� Value��%^   '��       �� 5"      " ��6   .�5#      ��  �@��CornerRadius$ 7��69   5$      5%      .��5&      ]� .+�5'      �� $��	staff.png��6   $��21��6/   $��24��6;   $��0,0,2,0q�6F   �� 5(      $��Register Staff ��6    $��Center=�67   5)      5*      �� 5+      $��Account Information��6   $��Italic$�62   $��Century Gothic)�6E   $��10(�6a   $	��Top=�6o   $
��Left=�6�   $��	8,193,0,0q�6�   � 5,      
�������$��1��6)   $��Center=�64   $��10,213,0,234.8q�6O   5-      5/      50      