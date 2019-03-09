package com.reportit.reportitbackend.impl;

import com.filter.textcorrector.TextFilter;
import com.filter.textcorrector.spellchecking.Language;
import com.reportit.reportitbackend.TextProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextProcessServiceImpl implements TextProcessService{

    private TextFilter textFilter = new TextFilter(Language.ENGLISH);

    public List<String> getWordSuggestions(String input) {
        return textFilter.checkWord(input);
    }

    public boolean checkForProfanity(String word) {
        List<String> compundWordList = textFilter.checkCompound(word);
        return textFilter.isProfane(word) || (!compundWordList.isEmpty() && textFilter
            .isProfane(compundWordList.get(0)));
    }

    //    String text = textFilter.preproccess("p13ce of sh1t", false);
}
