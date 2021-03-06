import { User } from "../classes/user";

export class News {
    public id: number;
    private likes: number;
    private dislikes: number;
    private date: Date;
    private user: User;

    public constructor(
        private text: string
    ) { }
}
