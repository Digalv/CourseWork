const HOST = 'http://localhost:8001/back?';
const KEY_TITLE = 'title';
const KEY_CAPITAL = 'capital';
const KEY_POPULATION = 'population';
const KEY_SQUARE = 'square';
const KEY_CONTINENT = 'continent';
const KEY_LANGUAGE = 'language';
let JsonArr = [];


fetch(HOST + "start=1", {
    method: 'POST',
}).then(response =>
    response.json().then(data => ({
        data: data,
        status: response.status
    })).then(res => {
        try {
            JsonArr = res.data;
            render(JsonArr);
        } catch {
            JsonArr = [];
        }
    }));



let $show = document.querySelector('.show')

function byField(field) {
    return (a, b) => a[field] < b[field] ? 1 : -1;
}

function filter(category) {
    JsonArr.sort(byField(category));
    if (category == 'title' || category == 'capital') {
        JsonArr.reverse();
    }
}

function render(array) {
    $show.innerHTML = '';
    for (i = 0; i < array.length; i++) {
        $show.innerHTML += '<p class="member card py-2 h4">' + (i + 1) + ") " + array[i][KEY_TITLE] + ' | ' + array[i][KEY_CAPITAL] + ' | ' + array[i][KEY_POPULATION] + ' | ' + array[i][KEY_SQUARE] + ' | ' + array[i][KEY_CONTINENT] + ' | ' + array[i][KEY_LANGUAGE] + '</p>'
    }
}


filter("title");
render(JsonArr);
/* */
/*Обработка чекбокса */
let click_filter_population = 0;
document.querySelector('.filter_population').addEventListener('change', function() {
    click_filter_population++;
    if (click_filter_population % 2 == 1) {
        filter('population');
        render(JsonArr);
    } else {
        filter('title');
        render(JsonArr);
    }
})

let click_filter_square = 0;
document.querySelector('.filter_square').addEventListener('change', function() {
        click_filter_square++;
        if (click_filter_square % 2 == 1) {
            filter('square');
            render(JsonArr);
        } else {
            filter('title');
            render(JsonArr);
        }
    })
    /* */

//Фильтр континента
let $filter_continent = document.querySelector('.filter_continent');
$filter_continent.addEventListener('change', function() {
    let result = [];
    if ($filter_continent.options[$filter_continent.selectedIndex].text == 'Default') {
        return render(JsonArr);
    }
    for (i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_CONTINENT] == $filter_continent.options[$filter_continent.selectedIndex].text) {
            result.push(JsonArr[i]);
        }
    }
    render(result);
})

/*Живой поиск */
let $search_capital = document.querySelector('.search_capital');
$search_capital.addEventListener('input', function() {
        let result = [];
        for (i = 0; i < JsonArr.length; i++) {
            if (JsonArr[i][KEY_CAPITAL].toLowerCase().includes($search_capital.value.toLowerCase())) {
                result.push(JsonArr[i]);
            }
        }
        render(result);
    })
    /* */
    //Поиск по языку
let $search_language = document.querySelector('.search_language');
$search_language.addEventListener('change', function() {
    let result = [];
    if ($search_language.options[$search_language.selectedIndex].text == 'Default') {
        return render(JsonArr);
    }
    for (i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_LANGUAGE] == $search_language.options[$search_language.selectedIndex].text) {
            result.push(JsonArr[i]);
        }
    }
    render(result);
})

//Поиск по площади
let $search_square = document.querySelector('.search_square');
$search_square.addEventListener('input', function() {
    let result = [];
    for (i = 0; i < JsonArr.length; i++) {
        if ($search_square.value < JsonArr[i][KEY_SQUARE]) {
            result.push(JsonArr[i]);
        }
    }
    render(result);
})


/**Выпад модального окна */
document.querySelector('.add').addEventListener('click', function() {
    document.querySelector('.modal_add').classList.add('visibl');
});

document.querySelector('.modal_add .overlay').addEventListener('click', function() {
    document.querySelector('.modal_add').classList.remove('visibl');
    render(JsonArr);
});

document.querySelector('.del').addEventListener('click', function() {
    document.querySelector('.modal_delete').classList.add('visibl');
});

document.querySelector('.modal_delete .overlay').addEventListener('click', function() {
    document.querySelector('.modal_delete').classList.remove('visibl');
    render(JsonArr);
});

/**Обработка модаль полей */

document.querySelector('.modul_add_send').addEventListener('click', function() {
    let result = {
        title: document.querySelector('.modal_input_name').value,
        capital: document.querySelector('.modal_input_capital').value,
        population: Number(document.querySelector('.modal_input_population').value),
        square: Number(document.querySelector('.modal_input_square').value),
        continent: document.querySelector('.modal_input_continent').options[document.querySelector('.modal_input_continent').selectedIndex].text,
        language: document.querySelector('.modal_input_language').options[document.querySelector('.modal_input_language').selectedIndex].text,
    };
    document.querySelector('.modal_input_name').value = "";
    document.querySelector('.modal_input_capital').value = "";
    document.querySelector('.modal_input_square').value = "";
    document.querySelector('.modal_input_population').value = "";


    fetch(HOST + "add=1", {
        method: 'POST',
        body: JSON.stringify(result),
    }).then(response =>
        response.json().then(data => ({
            data: data,
            status: response.status
        })).then(res => {
            if (JSON.stringify(res.data) == '{"Correct":"1"}') {
                document.querySelector('.modal_add').classList.remove('visibl');
                JsonArr.push(result);
                render(JsonArr);
                alert("adding successfully")
            } else {
                alert("Incorrect data, try again")
            }
        }));
})
document.querySelector('.modal_delete_send').addEventListener('click', function() {
    let result = [];
    for (i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_POPULATION] > document.querySelector('.modal_delete_input').value) {
            result.push(JsonArr[i]);
        }
    }
    JsonArr = result;
    document.querySelector('.modal_delete_input').value = "";
    document.querySelector('.modal_delete').classList.remove('visibl');
    render(JsonArr);

    fetch(HOST + "delete=1", {
        method: 'POST',
        body: JSON.stringify(result),
    }).then(response =>
        response.json().then(data => ({
            data: data,
            status: response.status
        })).then(res => {
            if (JSON.stringify(res.data) == '{"Correct":"1"}') {
                document.querySelector('.modal_delete').classList.remove('visibl');
                render(JsonArr);
                alert("deletion successful");
            } else {
                alert("Incorrect data, try again")
            }
        }));
})

//Редактирование записи

let edit_id = -1;
document.querySelector('.edit_member').addEventListener('change', function() {
    for (i = 0; i < JsonArr.length; i++) {
        if (document.querySelector('.edit_member').value == JsonArr[i][KEY_TITLE]) {
            document.querySelector('.modal_edit').classList.add('visibl');
            edit_id = i;
            break;
        }
    }
})

document.querySelector('.modal_edit .overlay').addEventListener('click', function() {
    document.querySelector('.modal_edit').classList.remove('visibl');
    render(JsonArr);
});
document.querySelector('.modal_edit_send').addEventListener('click', function() {
    let result = {
        title: document.querySelector('.modal_input_name_edit').value,
        capital: document.querySelector('.modal_input_capital_edit').value,
        population: Number(document.querySelector('.modal_input_population_edit').value),
        square: Number(document.querySelector('.modal_input_square_edit').value),
        continent: document.querySelector('.modal_input_continent_edit').options[document.querySelector('.modal_input_continent_edit').selectedIndex].text,
        language: document.querySelector('.modal_input_language_edit').options[document.querySelector('.modal_input_language_edit').selectedIndex].text,
    };
    document.querySelector('.modal_input_name_edit').value = "";
    document.querySelector('.modal_input_capital_edit').value = "";
    document.querySelector('.modal_input_square_edit').value = "";
    document.querySelector('.modal_input_population_edit').value = "";

    console.log(result);
    document.querySelector('.modal_edit').classList.remove('visibl');
    JsonArr[edit_id] = result;

    fetch(HOST + "edit=1", {
        method: 'POST',
        body: JSON.stringify(JsonArr),
    }).then(response =>
        response.json().then(data => ({
            data: data,
            status: response.status
        })).then(res => {
            if (JSON.stringify(res.data) == '{"Correct":"1"}') {
                document.querySelector('.modal_edit').classList.remove('visibl');
                render(JsonArr);
                alert("Edition successful");
            } else {
                alert("Incorrect data, try again")
            }
        }));
})