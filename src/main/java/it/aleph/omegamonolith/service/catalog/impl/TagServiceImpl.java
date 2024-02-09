package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.dao.catalog.TagRepository;
import it.aleph.omegamonolith.dto.catalog.tag.SearchTagsDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.catalog.TagDtoMapper;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.service.catalog.TagService;
import it.aleph.omegamonolith.specification.catalog.TagSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDtoMapper tagDtoMapper;
    private final List<ObjectFactory<TagSpecificationBuilder>> beanFactory;

    @Override
    public TagDto addTag(TagDto createTagDto) {
        Tag entity = tagDtoMapper.toEntity(createTagDto);
        return tagDtoMapper.toDto(tagRepository.save(entity));
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag tagObtained = accessResource(id);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public void removeTagById(Long id) {
        Tag tagObtained = accessResource(id);
        tagRepository.delete(tagObtained);
    }

    @Override
    public TagDto updateTagById(Long id, TagDto updateTagDto) {
        Tag tagObtained = accessResource(id);
        tagDtoMapper.update(tagObtained, updateTagDto);
        tagRepository.save(tagObtained);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("tag"));
        SearchTagsDto searchTagsDto = SearchTagsDto.builder().tag(tag).build();
        Page<Tag> pageOfTags = tagRepository.findAll(buildSpecification(searchTagsDto), pageable);
        return tagDtoMapper.toDtoList(pageOfTags.toList());
    }

    @Override
    public List<TagDto> findAllByIdList(List<Long> idTagList){
        List<Tag> tagListFound = tagRepository.findAllById(idTagList);
        if(tagListFound.size() != idTagList.size()){
            List<Long> idFoundList = tagListFound.stream().map(Tag::getId).toList();
            List<Long> idNotFoundList = idTagList.stream().filter(idFoundList::contains).toList();
            throw buildNotFoundException(idNotFoundList);
        }
        return tagDtoMapper.toDtoList(tagListFound);

    }

    private Specification<Tag> buildSpecification(SearchTagsDto searchTagsDto) {
        List<TagSpecificationBuilder> specificationBuilderList = beanFactory.stream().map(ObjectFactory::getObject).toList();
        return specificationBuilderList.stream()
                .map(specificationBuilder ->
                        specificationBuilder.setFilter(searchTagsDto).build())
                .reduce(Specification::and)
                .orElse(null);
    }



    private Tag accessResource(Long id){
        return tagRepository.findById(id).orElseThrow(() -> buildNotFoundException(List.of(id)));
    }



    private RuntimeException buildNotFoundException(List<Long> idList) {
        return NotFoundException.builder().idListNotFound(idList).message("The following id was not found: ").build();
    }

}
