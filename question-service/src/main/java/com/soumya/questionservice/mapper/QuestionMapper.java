package com.soumya.questionservice.mapper;

import com.soumya.questionservice.models.Question;
import com.soumya.questionservice.models.QuestionForUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE= Mappers.getMapper(QuestionMapper.class);

    QuestionForUser questionForUser(Question question);

}
