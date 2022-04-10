package com.example.test_java_task.common;

import com.example.test_java_task.model.pojo.Sector;
import com.example.test_java_task.repository.SectorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Stack;

@Component
public class SectorStorage {

    private final SectorRepository sectorRepository;
    private List<Sector> sectors;
    private String optionalsHtml;

    public SectorStorage(SectorRepository sectorRepository){
        this.sectorRepository = sectorRepository;
        sectors = sectorRepository.findAll();
        optionalsHtml = CreateHtmlOptionalText();
    }

    public String getOptionalsHtml() {
        return optionalsHtml;
    }

    public int checkSectorValues(List<Integer> values) {
        for (Integer value: values) {
            boolean check = true;
            for (Sector sector : sectors) {
                if (sector.getValue().equals(value)) {
                    check = false;
                }
            }
            if (check) return value.intValue();
        }
        return 0;
    }

    private String CreateHtmlOptionalText(){

        Stack<Sector> stack = new Stack<>();
        //put main sectors in stack
        sectors.stream()
                .filter(sector -> sector.getRelatedId().equals(0))
                .forEach(sector -> stack.push(sector));
        stack.sort((Sector h1, Sector h2) -> h2.getValue().compareTo(h1.getValue()));

        StringBuilder str = new StringBuilder();

        while (!stack.isEmpty()){
            Sector s = stack.pop();
            str.append("<option value=\""+s.getValue()+"\">");
            int spaces = s.getDepth();
            while (spaces-- > 0) {
                str.append("&nbsp;");
            }
            str.append(s.getName() + "</option>\n");
            sectors.stream()
                    .filter(sector -> sector.getRelatedId().equals(s.getValue()))
                    .sorted((Sector h1, Sector h2) -> h2.getName().compareTo(h1.getName()))
                    .forEach(sector -> stack.push(sector));
        }
        return  str.toString();
    }
}
