import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.List;

@Named
@RequestScoped
public class TwitterService {

    public void createTweet(String author, String content) {
        TwitterRepository tr = new TwitterRepository();
        tr.createTweet(author, content);
        System.out.println("sex");
    }

    public void addLike(int id) {
        TwitterRepository tr = new TwitterRepository();
        System.out.println("sex" + id);
        tr.addLike(id);
    }

    public List<Tweet> getAll() {
        TwitterRepository tr = new TwitterRepository();
        return tr.getAll();
    }

    public void deleteTweet(int id) {
        TwitterRepository tr = new TwitterRepository();
        tr.deleteTweet(id);
    }

    public void editTweet(int id, String author, String content) throws IOException {
        TwitterRepository tr = new TwitterRepository();
        tr.editTweet(id, author, content);
        FacesContext.getCurrentInstance().getExternalContext().redirect("mainPage.xhtml");
    }

    public Tweet getTweet() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        TwitterRepository tr = new TwitterRepository();
        return tr.getTweet(Integer.parseInt(id));
    }
}
