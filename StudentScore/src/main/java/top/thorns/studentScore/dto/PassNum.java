package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2621:49
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: PassNum
 * @Description: 统计及格人数
 **/
@Data
public class PassNum {

   /**
    * 总人数
    */
   private Integer total;

   /**
    * 语文及格人数
    */
   private Integer languageNum;

   /**
    * 数学及格人数
    */
   private Integer mathNum;

   /**
    *英语及格人数
    */
   private Integer englishNum;

   /**
    *政治及格人数
    */
   private Integer politicsNum;

   /**
    *历史及格人数
    */
   private Integer historyNum;

   /**
    *地理及格人数
    */
   private Integer geogNum;

}
