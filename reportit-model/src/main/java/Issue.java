import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Issue.COLLECTION_NAME_ISSUE)
public class Issue {
    public static final String COLLECTION_NAME_ISSUE = "issue_collection";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IMAGES = "images";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_CATEGORY = "category";

    @Field(value = Issue.FIELD_TITLE)
    private String title;

    @Field(value = Issue.FIELD_DESCRIPTION)
    private String description;

    @Field(value = Issue.FIELD_IMAGES)
    private List<String> images;

    @Field(value = Issue.FIELD_STATUS)
    private StatusEnum status;

    @Field(value = Issue.FIELD_CATEGORY)
    private CategoryEnum category;

}
