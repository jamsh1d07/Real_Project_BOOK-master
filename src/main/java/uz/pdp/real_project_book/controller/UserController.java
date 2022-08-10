package uz.pdp.real_project_book.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.real_project_book.model.Book;
import uz.pdp.real_project_book.model.Category;
import uz.pdp.real_project_book.model.History;
import uz.pdp.real_project_book.model.User;
import uz.pdp.real_project_book.payload.*;
import uz.pdp.real_project_book.repository_service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HistoryService historyService;

    static User currentUser = null;


    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto dto) {
        List<User> all = userService.findAll();
        for (User user : all) {
            if (user.getEmail().equals(dto.getEmail())) {
                currentUser = user; // kirgan userni ushlab olish
                if (user.getRole().equals("admin")) {
                    return "adminPage";
                } else {
                    return "userPage";
                }
            }
        }
        return "index";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto dto) {
        boolean added = false;
        List<User> all = userService.findAll();

        for (User user : all) {
            if (user.getEmail().equals(dto.getEmail())) {
                added = true;
                break;
            }
        }

        if (!added && dto.getConfirmPassword().equals(dto.getPassword())) {
            userService.save(new UserDto(dto.getUsername(), dto.getEmail(), dto.getPassword()));
        }
        return "index";

    }


    //==================================Category=======================================
    private final CategoryService categoryService;

    /**
     * add
     *
     * @param model
     * @return
     */
    @GetMapping("/category/crud")
    public String allCategories(Model model) {
        List<Category> all = categoryService.findAll();
        model.addAttribute("categoryList", all);
        return "category";
    }

    @GetMapping("/category/show")
    public String show(Model model) {
        model.addAttribute("message", "All Category");
        List<Category> all = categoryService.findAll();
        model.addAttribute("categoryList", all);
        return "category-show";
    }

    @PostMapping("/category/add")
    public String saveProduct(@ModelAttribute CategoryDTO categoryDTO, Model model) {

        String message = "";

        Category category = new Category();
        category.setName(categoryDTO.getName());

        int save = categoryService.save(category);

        if (save == 1) {
            // auditing :
            HistoryDto dto = new HistoryDto();
            dto.setUserId(currentUser.getId());
            dto.setAction("Add to category");
            dto.setObject("Category");
            dto.setObjectName(categoryDTO.getName());
            historyService.save(dto);

            message = "success";
        } else {
            message = "failed";
        }


        model.addAttribute("message", message);
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "edit_category";
    }

    @PostMapping("/category/edit/{id}")
    public String updateCategory(@PathVariable Integer id,
                                 @ModelAttribute("category") Category category,
                                 Model model) {

        String message = "";

        Category existCategory = categoryService.findById(id);
        existCategory.setName(category.getName());
        int update = categoryService.update(existCategory, id);

        if (update == 1) {
            HistoryDto dto = new HistoryDto();
            dto.setUserId(currentUser.getId());
            dto.setAction("editing category");
            dto.setObject("Category");
            dto.setObjectName(category.getName());

            historyService.save(dto);
            message = "success";
        } else {
            message = "fail";
        }


        model.addAttribute("message", message);
        model.addAttribute("categoryList", categoryService.findAll());
        return "category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, Model model) {

        String message = "";
        int delete = categoryService.delete(id);

        if (delete == 1) {
            Category category = categoryService.findById(id);

            HistoryDto dto = new HistoryDto();
            dto.setUserId(currentUser.getId());
            dto.setAction("delete category");
            dto.setObject("Category");
            dto.setObjectName(category.getName());
            historyService.save(dto);

            message = "success";

        } else {
            message = "failed";
        }

        model.addAttribute("message", message);
        model.addAttribute("categoryList", categoryService.findAll());
        return "category";
    }

    //==================================Category=======================================
    //==================================Book===========================================
    private final BookService bookService;


    @GetMapping("/book/crud")
    public String allBooks(Model model) {
        List<Book> all = bookService.findAll();
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookList", all);
        model.addAttribute("message", "All books are there");
        return "book";
    }

    @GetMapping("/book/show")
    public String shown(Model model) {
        model.addAttribute("message", "All Book Inform");
        List<Book> all = bookService.findAll();
        model.addAttribute("bookL", all);
        return "book-show";
    }

    @GetMapping("/book/add")
    public String bookSavePage(Model model) {

        List<Book> all = bookService.findAll();
        model.addAttribute("bookList", all);
        model.addAttribute("message", "All books");
        return "book";
    }

    @PostMapping("/book/add")
    public String add(@ModelAttribute BookDTO bookDTO, Model model) {

        // qo'shish kerak :
        int save = bookService.save(bookDTO);
        String message = "something went wrong";

        if (save == 1) {
            message = "Added";


            HistoryDto dto = new HistoryDto();
            dto.setUserId(currentUser.getId());
            dto.setAction("saving book");
            dto.setObject("Book");
            dto.setObjectName(bookDTO.getName());

            historyService.save(dto);
            message = "success";

        } else {
            message = "failed";
        }

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        List<Book> all = bookService.findAll();
        Category category = categoryService.findById(bookDTO.getCategoryId());
        model.addAttribute("cat", category);
        model.addAttribute("message", message);
        model.addAttribute("bookList", all);

        return "book";
    }

    @GetMapping("/book/edit/{id}")
    public String editBooksForm(@PathVariable Integer id, Model model) {


        Book book = bookService.findById(id);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("book", book);

        return "edit_book";
    }

    @PostMapping("/book/edit/{id}")
    public String editBook(Book book, @ModelAttribute("category") Integer catId,
                           Model model) {

        BookDTO bookDTO = new BookDTO(book.getName(), book.getPrice(), catId);

        int update = bookService.update(bookDTO, book.getId());

        String message = "something went wrong";
        if (update == 1) {
            message = "Updated";

            HistoryDto dto = new HistoryDto();
            dto.setUserId(currentUser.getId());
            dto.setAction("editing book");
            dto.setObject("Book");
            dto.setObjectName(bookDTO.getName());

            historyService.save(dto);
            message = "success";

        }

        List<Book> all = bookService.findAll();

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("message", message);
        model.addAttribute("bookList", all);

        return "book";

    }

    @GetMapping("/book/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model) {

        String message = "";

        Book book = bookService.findById(id);
        int delete = bookService.delete(id);

        HistoryDto dto = new HistoryDto();
        dto.setUserId(currentUser.getId());
        dto.setAction("editing category");
        dto.setObject("Category");
        dto.setObjectName(book.getName());
        historyService.save(dto);

        message = "success";


        message = delete == 1 ? "Success" : "Failed";

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("message", message);
        model.addAttribute("bookList", bookService.findAll());
        return "book";
    }

    //===================================history==============
    @GetMapping("/history")
    public void downloadFile(HttpServletResponse response) {
        try {
            // file generate

            File actions = new File("src/main/resources/actions.pdf");
            FileOutputStream fileOutputStream = null;

            fileOutputStream = new FileOutputStream(actions);
            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            pdfDocument.addNewPage();
            Paragraph paragraph = new Paragraph("Recent Actions");
            paragraph.setBold();
            paragraph.setFontSize(22);
            paragraph.setTextAlignment(TextAlignment.CENTER);

            document.add(paragraph);

            Table table = new Table(6);

            table.addCell("Id ");
            table.addCell("user id");
            table.addCell("created at");
            table.addCell("action type");
            table.addCell("object type");
            table.addCell("object name");

            List<History> historyList = historyService.findAll();

            for (History history : historyList) {
                table.addCell(history.getId()+"");
                table.addCell(history.getUserId()+"");
                table.addCell(history.getCreatedAt()+"");
                table.addCell(history.getAction()+"");
                table.addCell(history.getObject()+"");
                table.addCell(history.getObjectName()+"");
            }

            document.add(table);
            document.close();
            fileOutputStream.close();



            response.setHeader("Content-Disposition",
                    "attachment; filename = \""
                            + actions.getName() + "\"");

            //type:
            response.setContentType(Files.probeContentType(actions.toPath()));// nima bo'lasa shuni ayatadi
            
            // content :
            FileCopyUtils.copy(Files.readAllBytes(actions.toPath()), response.getOutputStream());





        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //==================================Book===========================================

    // xatolik bo'lsa requiredlarni ko'rsatadi
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
