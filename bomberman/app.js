
const container = document.querySelector('.container');
const i_board = document.querySelector('.i_board');
const board_btn = document.querySelector('.board_btn');
const i_player_pos = document.querySelector('.i_player_pos');
const player_pos_btn = document.querySelector('.player_pos_btn');
const i_key_pos = document.querySelector('.i_key_pos');
const key_pos_btn = document.querySelector('.key_pos_btn');
const i_villans = document.querySelector('.i_villans');
const villans_btn = document.querySelector('.villans_btn');
const i_bricks = document.querySelector('.i_bricks');
const bricks_btn = document.querySelector('.bricks_btn');
const start_btn = document.querySelector('.start_btn');

let board = null, N = null, player_pos = null, key_pos = null, villans = [], bricks = [], walls = [];
let villan_movement = null;

board_btn.addEventListener('click', function () {
    N = i_board.value;
    board = document.createElement('DIV');
    board.style.height = `${N * 40}px`;
    board.style.width = `${N * 40}px`;
    board.classList.add('board');
    container.appendChild(board);

    for (let r = 0; r < N; r++) {
        for (let c = 0; c < N; c++) {
            const square = document.createElement('DIV');
            square.classList.add(`${String.fromCharCode(65 + r - 1)}${String.fromCharCode(65 + c - 1)}`);
            square.classList.add('square');
            board.appendChild(square);

            if (r == 0 && c == 0) continue;

            if (r == 0 && c > 0) square.innerHTML = String.fromCharCode(65 + c - 1);
            if (c == 0 && r > 0) square.innerHTML = String.fromCharCode(65 + r - 1);

            if ((r == 1 && c >= 1) || (c == 1 && r >= 1) || (r == N - 1 && c >= 1) || (c == N - 1 && r >= 1) || (r >= 3 && r < N - 2 && c >= 3 && c < N - 2 && r % 2 && c % 2)) {
                square.innerHTML = '*';
                walls.push(String(String.fromCharCode(65 + r - 1) + String.fromCharCode(65 + c - 1)));
            }
        }
    }
});


player_pos_btn.addEventListener('click', function () {
    player_pos = i_player_pos.value;
    document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML = 'P';
});


key_pos_btn.addEventListener('click', function () {
    key_pos = i_key_pos.value;
    document.getElementsByClassName(`${key_pos[0]}${key_pos[1]}`)[0].innerHTML = 'K';
});


villans_btn.addEventListener('click', function () {
    villans = i_villans.value.split(",");
    for (let v = 0; v < villans.length; v++) {
        document.getElementsByClassName(`${villans[v][0]}${villans[v][1]}`)[0].innerHTML = 'V';
    }
});


bricks_btn.addEventListener('click', function () {
    bricks = i_bricks.value.split(",");
    for (let b = 0; b < bricks.length; b++) {
        document.getElementsByClassName(`${bricks[b][0]}${bricks[b][1]}`)[0].innerHTML = 'B';
    }
});

start_btn.addEventListener('click', play);

function play() {

    villan_movement = setInterval(moveVillans, 1000);

    document.body.addEventListener('keydown', function (e) {
        switch (e.key) {
            case 'a':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) - 1), String.fromCharCode(player_pos.charCodeAt(1)));
                break;
            case 's':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) + 1), String.fromCharCode(player_pos.charCodeAt(1)));
                break;
            case 'd':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0)), String.fromCharCode(player_pos.charCodeAt(1) - 1));
                break;
            case 'f':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0)), String.fromCharCode(player_pos.charCodeAt(1) + 1));
                break;
            case 'g':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) - 1), String.fromCharCode(player_pos.charCodeAt(1) - 1));
                break;
            case 'h':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) - 1), String.fromCharCode(player_pos.charCodeAt(1) + 1));
                break;
            case 'j':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) + 1), String.fromCharCode(player_pos.charCodeAt(1) - 1));
                break;
            case 'k':
                movePlayer(String.fromCharCode(player_pos.charCodeAt(0) + 1), String.fromCharCode(player_pos.charCodeAt(1) + 1));
                break;
            case 'x':
                const s = player_pos;
                put_bomb(s);
        }
    });
}


function movePlayer(r, c) {

    if (document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML !== 'X') document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML = "";

    if (r + c === key_pos) {
        key_pos = null;
        player_pos = r + c;
        document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML = 'P';
        setTimeout(function () {
            alert("You won");
        }, 400);
        clearInterval(villan_movement);
    }
    else if (walls.includes(r + c) || bricks.includes(r + c)) document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML = 'P';
    else if (villans.includes(r + c)) {
        player_pos = null;
        setTimeout(function () {
            alert("You lost");
        }, 400);
        clearInterval(villan_movement);
    }
    else {
        player_pos = r + c;
        document.getElementsByClassName(`${player_pos[0]}${player_pos[1]}`)[0].innerHTML = 'P';
    }
}

function moveVillan(r, c, v) {
    document.getElementsByClassName(`${villans[v][0]}${villans[v][1]}`)[0].innerHTML = "";

    if (document.getElementsByClassName(`${r}${c}`)[0].innerHTML === '*' || document.getElementsByClassName(`${r}${c}`)[0].innerHTML === 'B' || document.getElementsByClassName(`${r}${c}`)[0].innerHTML === 'K' || document.getElementsByClassName(`${r}${c}`)[0].innerHTML === 'V') {
        document.getElementsByClassName(`${villans[v][0]}${villans[v][1]}`)[0].innerHTML = 'V';
    }
    else if (document.getElementsByClassName(`${r}${c}`)[0].innerHTML === 'P') {
        player_pos = null;
        villans[v] = r+c;
        document.getElementsByClassName(`${villans[v][0]}${villans[v][1]}`)[0].innerHTML = 'V';
        setTimeout(function () {
            alert("You lost");
        }, 400);
        clearInterval(villan_movement);
    }
    else {
        villans[v] = r + c;
        document.getElementsByClassName(`${villans[v][0]}${villans[v][1]}`)[0].innerHTML = 'V';
    }
}

function moveVillans() {
    let v = 0;
    for (; v < villans.length; v++) {
        const d = Math.floor(Math.random() * 8);

        switch (d) {
            case 0:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) - 1), String.fromCharCode(villans[v].charCodeAt(1)), v);
                break;
            case 1:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) + 1), String.fromCharCode(villans[v].charCodeAt(1)), v);
                break;
            case 2:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0)), String.fromCharCode(villans[v].charCodeAt(1) - 1), v);
                break;
            case 3:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0)), String.fromCharCode(villans[v].charCodeAt(1) + 1), v);
                break;
            case 4:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) - 1), String.fromCharCode(villans[v].charCodeAt(1) - 1), v);
                break;
            case 5:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) - 1), String.fromCharCode(villans[v].charCodeAt(1) + 1), v);
                break;
            case 6:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) + 1), String.fromCharCode(villans[v].charCodeAt(1) - 1), v);
                break;
            case 7:
                moveVillan(String.fromCharCode(villans[v].charCodeAt(0) + 1), String.fromCharCode(villans[v].charCodeAt(1) + 1), v);
                break;
        }
    }
}


function put_bomb(s) {
    document.getElementsByClassName(`${s[0]}${s[1]}`)[0].innerHTML = 'X';
    setTimeout(function () {
        document.getElementsByClassName(`${s[0]}${s[1]}`)[0].innerHTML = "";
    }, 2000);
}
