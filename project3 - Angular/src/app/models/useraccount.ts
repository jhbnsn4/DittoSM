import { IImageMap } from "./imagemap";
import { IPost } from "./post";

export interface IUserAccount {
    userId: number;
    username: string;
    password: string;
    userEmail: string;
    firstName: string;
    lastName: string;
    birthday: string;
    statusText: string;
    profilePicture: string;
    postList: IPost[]|null;
    dittoFollowerList: IUserAccount[]|null;
    dittoFollowingList: IUserAccount[]|null;
}
