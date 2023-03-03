package top.thorns.studentScore.dto;

import lombok.Data;
import top.thorns.studentScore.dto.scoreMax.*;

import java.util.List;

/**
 * @Author: Thorns
 * @Data:2023/3/215:43
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: ScoreMaxDto
 * @Description: 各科最高分dto
 **/
@Data
public class ScoreMaxDto {

    private List<total> totalMaxList;

    private List<language> languageMaxList;

    private List<math> mathMaxList;

    private List<english> englishMaxList;

    private List<politics> politicsMaxList;

    private List<history> historyMaxList;

    private List<geog> geogMaxList;

    public ScoreMaxDto(List<total> totalMaxList, List<language> languageMaxList, List<math> mathMaxList, List<english> englishMaxList, List<politics> politicsMaxList, List<history> historyMaxList, List<geog> geogMaxList) {
        this.totalMaxList = totalMaxList;
        this.languageMaxList = languageMaxList;
        this.mathMaxList = mathMaxList;
        this.englishMaxList = englishMaxList;
        this.politicsMaxList = politicsMaxList;
        this.historyMaxList = historyMaxList;
        this.geogMaxList = geogMaxList;
    }
}
